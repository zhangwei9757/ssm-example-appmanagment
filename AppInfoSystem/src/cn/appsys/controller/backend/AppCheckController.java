package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.dao.AppInfoMapper;
import cn.appsys.dao.AppVersionMapper;
import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppInfoExample;
import cn.appsys.pojo.AppInfoExample.Criteria;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/manager/backend/app")
public class AppCheckController {
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
			@RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {
		PageHelper.startPage(pageIndex != null ? Integer.parseInt(pageIndex)
				: 1, Constants.pageSize);// 分页特效
		List<AppInfo> appInfoList = this.appInfoMapper.getAppInfoList(
				isNull(querySoftwareName) != null ? querySoftwareName : null,
				1L, stl(queryCategoryLevel1), stl(queryCategoryLevel2),
				stl(queryCategoryLevel3), stl(queryFlatformId), null,
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
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		model.addAttribute("queryFlatformId", queryFlatformId);
		if (null != isNull(queryCategoryLevel2) || null != isNull(queryCategoryLevel1)) {
			List<AppCategory> categoryLevel2List = getAppCategoryByParentId(Long
					.valueOf(queryCategoryLevel1));
			model.addAttribute("categoryLevel2List", categoryLevel2List);// model保存APP二级分类列表
		}
		if (null != isNull(queryCategoryLevel3) || null != isNull(queryCategoryLevel2)) {
			List<AppCategory> categoryLevel3List = getAppCategoryByParentId(Long
					.valueOf(queryCategoryLevel2));
			model.addAttribute("categoryLevel3List", categoryLevel3List);// model保存APP三级分类列表
		}
		return "backend/applist";
	}

	/**
	 * 根据用户一级选择菜单显示二级菜单JSON列表
	 * **/
	@ResponseBody
	@RequestMapping(value = "/categorylevellist.json", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	public String categorylevellistJson(@RequestParam("pid") String pid) {
		return JSON.toJSONString(this.getAppCategoryByParentId(stl(pid)));
	}

	/**
	 * 根据指定APP的ID获取对应详细信息，根据指定版本ID获取对应详细信息
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(
			@RequestParam(value = "aid", required = false) String appId,
			@RequestParam(value = "vid", required = false) String versionId,
			Model model) {
		AppInfo appInfo = appInfoMapper
				.getAppInfo(Integer.valueOf(appId), null);
		AppVersion appVersion = appVersionMapper.selectByPrimaryKey(Long
				.valueOf(versionId));
		model.addAttribute(appVersion);
		model.addAttribute(appInfo);
		return "backend/appcheck";
	}

	/**
	 * 根据指定APP的ID获取对应详细信息，根据指定版本ID获取对应详细信息,修改信息
	 */
	@RequestMapping(value = "/checksave", method = RequestMethod.PUT)
	public String checksave(AppInfo appInfo) {
		AppInfoExample example = new AppInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(appInfo.getId());
		int updateRow = appInfoMapper
				.updateByExampleSelective(appInfo, example);
		return 1 == updateRow ? "redirect:/manager/backend/app/list"
				: "backend/appcheck";
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
