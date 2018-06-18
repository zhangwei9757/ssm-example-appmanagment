package cn.appsys.controller.developer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.dao.AppInfoMapper;
import cn.appsys.dao.AppVersionMapper;
import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppInfoExample;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.AppVersionExample;
import cn.appsys.pojo.AppVersionExample.Criteria;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;
import cn.appsys.tools.ErrorStatus;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping(value = "/dev/flatform/app")
public class AppController {
	@Resource
	private AppInfoMapper appInfoMapper;
	@Resource
	private AppVersionMapper appVersionMapper;
	@Resource
	private AppCategoryMapper appCategoryMapper;
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	@RequestMapping(value = "/list")
	public String getAppList(
			Model model,
			HttpSession session,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String queryStatus,
			@RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {
		Long devId = ((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId();// 开发者用户ID
		PageHelper.startPage(pageIndex != null ? Integer.parseInt(pageIndex)
				: 1, Constants.pageSize);// 分页特效
		List<AppInfo> appInfoList = this.appInfoMapper.getAppInfoList(
				isNull(querySoftwareName) != null ? querySoftwareName : null,
				stl(queryStatus), stl(queryCategoryLevel1),
				stl(queryCategoryLevel2), stl(queryCategoryLevel3),
				stl(queryFlatformId), devId,
				isNull(pageIndex) != null ? Integer.valueOf(pageIndex) : null,
				Integer.valueOf(Constants.pageSize));// 开发者APP所有集合列表
		PageInfo<AppInfo> pageInfo = new PageInfo<>(appInfoList);// 分页信息
		PageSupport pages = new PageSupport();// 分页pojo实体
		pages.setCurrentPageNo(pageInfo.getPageNum());
		pages.setPageSize(Constants.pageSize);
		Long pageTotal = pageInfo.getTotal();
		pages.setTotalCount(Integer.parseInt(pageTotal.toString()));

		model.addAttribute("pages", pages);// model保存分页pojo实体
		model.addAttribute("appInfoList", appInfoList);// model保存开发者APP所有集合列表
		List<DataDictionary> statusList = getDataDictionaryByTypeCode("APP_STATUS");// 所有APP状态列表
		List<DataDictionary> flatFormList = getDataDictionaryByTypeCode("APP_FLATFORM");
		List<AppCategory> categoryLevel1List = getAppCategoryByParentId(null);// 列出一级分类列表，注：二级和三级分类列表通过异步ajax获取
		model.addAttribute("statusList", statusList);// model保存APP状态列表
		model.addAttribute("flatFormList", flatFormList);// model保存APP所属平台列表
		model.addAttribute("categoryLevel1List", categoryLevel1List);// model保存APP一级分类列表

		// 方便用户查看已选择项目
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);

		if (null != isNull(queryCategoryLevel2)
				|| null != isNull(queryCategoryLevel1)) {
			List<AppCategory> categoryLevel2List = getAppCategoryByParentId(Long
					.valueOf(queryCategoryLevel1));
			model.addAttribute("categoryLevel2List", categoryLevel2List);// model保存APP二级分类列表
		}
		if (null != isNull(queryCategoryLevel3)
				|| null != isNull(queryCategoryLevel2)) {
			List<AppCategory> categoryLevel3List = getAppCategoryByParentId(Long
					.valueOf(queryCategoryLevel2));
			model.addAttribute("categoryLevel3List", categoryLevel3List);// model保存APP三级分类列表
		}
		return "developer/appinfolist";
	}

	/**
	 * 上架APP,下架APP
	 */
	@ResponseBody
	@RequestMapping(value = "/sale.json", method = RequestMethod.PUT)
	public String saleJson(
			@RequestParam(value = "appId", required = false) Integer appId) {
		
		
		
		return null;
	}

	/**
	 * 根据appId查询是历史所有版本信息列表，并跳转到修改版本界面
	 * **/
	@RequestMapping("/appversionmodify")
	public String appversionmodify(
			@RequestParam(value = "vid", required = false) String vid,
			@RequestParam(value = "aid", required = false) String aid,
			Model model) {
		List<AppVersion> appVersionList = this.appVersionMapper
				.getAppVersionList(Integer.valueOf(aid));
		model.addAttribute("appVersionList", appVersionList);// 指定AppId的所有版本列表
		AppVersion appVersion = appVersionMapper.selectByPrimaryKey(Long
				.valueOf(vid));
		model.addAttribute("appVersion", appVersion);// 指定AppId的详情
		return "developer/appversionmodify";
	}

	@ResponseBody
	@RequestMapping(value = "/delfile.json", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	public String delfileJson(
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam(value = "id", required = false) String id) {
		int row = -1;// 数据表删除的结果
		if (null == isNull(id) || null == isNull(flag)) {
			return JSON.toJSONString("failed");// 全部找不到表示删除失败
		}
		if ("logo".equals(flag)) {// 表示删除logo文件
			AppInfo appInfo = appInfoMapper
					.selectByPrimaryKey(Long.valueOf(id));
			File file = new File(appInfo.getLogoLocPath());
			if (file.exists()) {// 先删除物理文件，再删除数据库表对应的数据
				file.delete();
			}
			row = appInfoMapper.updateAppInfoById(Integer.valueOf(appInfo
					.getId().toString()));// 置空文件路径列
		} else if ("apk".equals(flag)) {// 表示删除apk文件
			AppVersion appVersion = appVersionMapper.selectByPrimaryKey(Long
					.valueOf(id));
			File file = new File(appVersion.getApkLocPath());
			if (file.exists()) {// 先删除物理文件，再删除数据库表对应的数据
				file.delete();
			}
			row = appVersionMapper.updateAppVersionById(Integer.valueOf(id));
		} else {
			return JSON.toJSONString("failed");// 全部找不到表示删除失败
		}
		return 1 == row ? JSON.toJSONString("success") : JSON
				.toJSONString("failed");
	}

	/**
	 * 根据图片后缀识别上传图片类型是否合格？
	 * **/
	public boolean getPicByPerfix(String prefix) {
		if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
				|| prefix.equalsIgnoreCase("jpeg")
				|| prefix.equalsIgnoreCase("pneg")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存修改后的appVersion信息，成功跳转到list所在handle控制器，失败返回对应信息并返回到当前修改界面
	 * **/
	@RequestMapping(value = "/appversionmodifysave", method = RequestMethod.POST)
	public String appversionmodifysave(
			AppVersion appVersion,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "attach", required = false) MultipartFile attach) {
		String downloadLink = null;// 下载的相对路径
		String apkLocPath = null;// APK文件的本地路径
		String apkFileName = null;// 要保存的真实文件名
		String realPath = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadfiles");// 物理路径所在的文件夹所在的绝对路径
		if (!attach.isEmpty()) {
			String oldFileName = attach.getOriginalFilename();// 上传的文件原名称
			String oldFilePrefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
			if (oldFilePrefix.equalsIgnoreCase("apk")) {// 文件上传过大
				AppInfo appInfo = appInfoMapper
						.getAppInfo(Integer.valueOf(appVersion.getAppId()
								.toString()), null);
				String apkName = appInfo.getAPKName();
				if (null == isNull(apkName)) {
					return "redirect:/dev/flatform/app/appversionmodify?vid="
							+ appVersion.getId() + "&aid="
							+ appVersion.getAppId() + "&error=1111";
				}
				apkFileName = apkName + "-" + appVersion.getVersionNo()
						+ ".apk";// 要保存的真实文件名
				File targetFile = new File(realPath, apkFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appversionmodify?vid="
							+ appVersion.getId() + "&aid="
							+ appVersion.getAppId() + "&error=2222";
				}
				downloadLink = request.getContextPath()
						+ "/statics/uploadfiles/" + apkFileName;// 下载的相对路径
				apkLocPath = realPath + File.separator + apkFileName;// APK文件的本地路径
			} else {
				return "redirect:/dev/flatform/app/appversionmodify?vid="
						+ appVersion.getId() + "&aid=" + appVersion.getAppId()
						+ "&error=3333";
			}
			appVersion.setModifyBy(((DevUser) session
					.getAttribute(Constants.DEV_USER_SESSION)).getId());
			appVersion.setModifyDate(new Date());
			appVersion.setModifyDate(new Date());
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setApkFileName(apkFileName);
		}
		AppVersionExample example = new AppVersionExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(appVersion.getId());
		int row = appVersionMapper
				.updateByExampleSelective(appVersion, example);
		if (1 == row) {
			return "redirect:/dev/flatform/app/list";
		}
		return "developer/appversionmodify";
	}

	/**
	 * 根据用户修改的信息提交后，进行文件上传处理，失败返回对应界面，成功提交返回列表界面
	 * **/
	@RequestMapping(value = "/appinfomodifysave", method = RequestMethod.POST)
	public String appinfomodifysave(
			AppVersion appVersion,
			AppInfo appInfo,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "attach", required = false) MultipartFile attach) {
		String logoPicPath = null;// 图片相对服务器路径
		String logoLocPath = null;// 图片相对服务器绝对路径
		String realPath = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadfiles");// 物理路径所在的文件夹所在的绝对路径
		if (!attach.isEmpty()) {
			String oldFileName = attach.getOriginalFilename();// 上传的文件原名称
			String oldFilePrefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
			int filesize = 500000;
			if (attach.getSize() > filesize) {// 文件上传过大
				return "redirect:/dev/flatform/app/appinfomodify?id="
						+ appInfo.getId() + "&error=4444";
			} else if (getPicByPerfix(oldFilePrefix)) {// 识别图片后缀是否合格
				String newFileName = appInfo.getAPKName() + ".jpg";// 指定新文件全名称，保持数据的唯一性
				File saveFile = new File(realPath, newFileName);
				if (!saveFile.exists()) {
					saveFile.mkdirs();// 不存在则创建
				}
				try {
					attach.transferTo(saveFile);// 保存文件到指定绝对路径下
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appinfomodify?id="
							+ appInfo.getId() + "&error=2222";
				}
				logoPicPath = request.getContextPath()
						+ "/statics/uploadfiles/" + newFileName;
				logoLocPath = realPath + File.separator + newFileName;
			} else {
				return "redirect:/dev/flatform/app/appinfomodify?id="
						+ appInfo.getId() + "&error=3333";// 后缀不合格返回
			}
		}
		// 把对应修改的信息保存
		appInfo.setModifyBy(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		AppInfoExample example = new AppInfoExample();
		cn.appsys.pojo.AppInfoExample.Criteria criteria = example
				.createCriteria();
		criteria.andIdEqualTo(appInfo.getId());
		int updateByExample = appInfoMapper.updateByExampleSelective(appInfo,
				example);// 执行修改操作
		if (1 == updateByExample) {
			return "redirect:/dev/flatform/app/list";// 成功返回列表界面
		}
		return "developer/appinfoadd";
	}

	/**
	 * 根据appId修改信息，并跳转到修改界面
	 * **/
	@RequestMapping(value = "/appinfomodify", method = RequestMethod.GET)
	public String modifyAppInfo(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "error", required = false) String fileUploadError,
			Model model) {
		if (null != isNull(fileUploadError)) {
			model.addAttribute("fileUploadError", ErrorStatus
					.getErrorStatusByCode(Integer.valueOf(fileUploadError))
					.getErrorMessage());// 根据指定错误编码,获取错误类型
		}
		AppInfo appInfo = appInfoMapper.getAppInfo(Integer.valueOf(id), null);// 根据Id获取对应实体对象，并跳转
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}

	/**
	 * 新增APP版本信息,提交成功返回appversionadd列表界面
	 * **/
	@RequestMapping("/addversionsave")
	public String addversionsave(
			AppVersion appVersion,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "a_downloadLink", required = false) MultipartFile attach) {
		String downloadLink = null;// 相对下载路径
		String apkLocPath = null;// 相对路径的绝对路径
		String apkFileName = null;// 上传的文件名字带后缀
		String realPath = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadfiles");
		if (!attach.isEmpty()) {
			String oldFileName = attach.getOriginalFilename();// 上传的文件原名称
			String oldFilePrefix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
			if (oldFilePrefix.equalsIgnoreCase("apk")) {
				String apkName = (appInfoMapper
						.getAppInfo(Integer.valueOf(appVersion.getAppId()
								.toString()), null)).getAPKName();// 获取对应上传文件的名字
				if (null == isNull(apkName)) {// 上传信息不完整(必须在app基本信息中匹配名字)
					return "redirect:/dev/flatform/app/appversionadd?id="
							+ appVersion.getAppId() + "&error=1111";
				}
				apkFileName = apkName + "-" + appVersion.getVersionNo()
						+ ".apk";// 上传版本号完整名字
				File targetFile = new File(realPath, apkFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();// 不存在则创建路径
				}
				try {
					attach.transferTo(targetFile);// 保存此文件
				} catch (Exception e) {
					e.printStackTrace();// 表示上传失败
					return "redirect:/dev/flatform/app/appversionadd?id="
							+ appVersion.getAppId() + "&error=2222";
				}
				downloadLink = request.getContextPath()
						+ "/statics/uploadfiles/" + apkFileName;// 拼出完整相对下载路径
				apkLocPath = realPath + File.separator + apkFileName;// 拼出完整相对下载绝对路径
			} else {
				return "redirect:/dev/flatform/app/appversionadd?id="
						+ appVersion.getAppId() + "&error=3333";
			}
		}
		appVersion.setCreatedBy(((DevUser) session
				.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setCreationDate(new Date());
		appVersion.setModifyDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		int row = appVersionMapper.insertSelective(appVersion);
		if (1 == row) {
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/dev/flatform/app/appversionadd?id="
				+ appVersion.getAppId();// 上传操作失败回显错误
	}

	/**
	 * 根据appId查询是历史所有版本信息列表，并跳转到新增界面
	 * **/
	@RequestMapping("/appversionadd")
	public String appversionAdd(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "error", required = false) String fileUploadError,
			AppVersion appVersion, Model model) {
		appVersion.setAppId(Long.valueOf(id));// 把传入的值放到这个appVersion对象中
		List<AppVersion> appVersionList = this.appVersionMapper
				.getAppVersionList(Integer.valueOf(id));// 指定AppId的所有版本列表
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute(appVersion);
		if (null != isNull(fileUploadError)) {
			model.addAttribute("fileUploadError", ErrorStatus
					.getErrorStatusByCode(Integer.valueOf(fileUploadError))
					.getErrorMessage());
		}
		return "developer/appversionadd";
	}

	/**
	 * 根据appId查询是详情信息,并跳转到view界面显示
	 * **/
	@RequestMapping(value = "/appview/{appinfoid}", method = RequestMethod.GET)
	public String appviewJson(@PathVariable("appinfoid") String appinfoid,
			Model model) {
		AppInfo appInfo = this.appInfoMapper.getAppInfo(
				Integer.valueOf(appinfoid), null);// 指定AppId的详情信息
		model.addAttribute("appInfo", appInfo);
		List<AppVersion> appVersionList = this.appVersionMapper
				.getAppVersionList(Integer.valueOf(appinfoid));// 指定AppId的所有版本列表
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appinfoview";
	}

	/**
	 * 根据appId查询是否存在，存在则删除
	 * **/
	@ResponseBody
	@RequestMapping(value = "/delapp.json", method = RequestMethod.GET)
	public String delappJson(
			@RequestParam(value = "id", required = false) String id) {
		AppInfo selectByPrimaryKey = appInfoMapper.selectByPrimaryKey(Long
				.valueOf(id));// 匹配是否存在
		if (null == selectByPrimaryKey) {
			return JSON.toJSONString("notexist");
		}
		int row = appInfoMapper.deleteByPrimaryKey(Long.valueOf(id));
		return 1 == row ? JSON.toJSONString("true") : JSON
				.toJSONString("false");
	}

	/**
	 * 根据用户一级选择菜单显示二级菜单JSON列表
	 * **/
	@ResponseBody
	@RequestMapping(value = "/categorylevellist.json", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	public String categorylevellistJson(
			@RequestParam(value = "pid", required = false) String pid) {
		return JSON.toJSONString(this.getAppCategoryByParentId(stl(pid)));
	}

	/**
	 * 根据tcode动态加载所属平台列表
	 * **/
	@ResponseBody
	@RequestMapping(value = "/datadictionarylist.json", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	public String datadictionarylistJson(
			@RequestParam(value = "tcode", required = false) String tcode) {
		return JSON.toJSONString(this.getDataDictionaryByTypeCode(tcode));
	}

	/**
	 * 判断是空或者“”
	 */
	public String isNull(String str) {
		return str != null && !str.isEmpty() ? str.trim() : null;
	}

	/**
	 * 非空转Long，空转Null
	 */
	public Long stl(String str) {//
		return isNull(str) != null ? Long.valueOf(str) : null;
	}

	/**
	 * 根据指定parentId获取所有分类列表
	 */
	public List<AppCategory> getAppCategoryByParentId(Long parentId) {
		return appCategoryMapper.getCateListByParentId(parentId);
	}

	/**
	 * 根据指定typeCode获取所有字典列表
	 */
	public List<DataDictionary> getDataDictionaryByTypeCode(String TypeCode) {
		return dataDictionaryMapper.getDictionarieListByTypeCode(TypeCode);
	}
}
