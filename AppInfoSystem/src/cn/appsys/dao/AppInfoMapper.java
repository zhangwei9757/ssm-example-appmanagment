package cn.appsys.dao;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppInfoMapper {

	public int updateAppInfoById(@Param("id") Integer id);

	public AppInfo getAppInfo(@Param(value = "id") Integer id,
			@Param(value = "APKName") String APKName);

	public List<AppInfo> getAppInfoList(
			@Param(value = "softwareName") String querySoftwareName,
			@Param(value = "status") Long queryStatus,
			@Param(value = "categoryLevel1") Long queryCategoryLevel1,
			@Param(value = "categoryLevel2") Long queryCategoryLevel2,
			@Param(value = "categoryLevel3") Long queryCategoryLevel3,
			@Param(value = "flatformId") Long queryFlatformId,
			@Param(value = "devId") Long devId,
			@Param(value = "from") Integer currentPageNo,
			@Param(value = "pageSize") Integer pageSize);

	public List<AppInfo> getAppInfoListFlush(
			@Param(value = "softwareName") String querySoftwareName,
			@Param(value = "status") Long queryStatus,
			@Param(value = "categoryLevel1") Long queryCategoryLevel1,
			@Param(value = "categoryLevel2") Long queryCategoryLevel2,
			@Param(value = "categoryLevel3") Long queryCategoryLevel3,
			@Param(value = "flatformId") Long queryFlatformId,
			@Param(value = "devId") Long devId,
			@Param(value = "from") Integer currentPageNo,
			@Param(value = "pageSize") Integer pageSize);

	int countByExample(AppInfoExample example);

	int deleteByExample(AppInfoExample example);

	int deleteByPrimaryKey(Long id);

	int insert(AppInfo record);

	int insertSelective(AppInfo record);

	List<AppInfo> selectByExample(AppInfoExample example);

	AppInfo selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") AppInfo record,
			@Param("example") AppInfoExample example);

	int updateByExample(@Param("record") AppInfo record,
			@Param("example") AppInfoExample example);

	int updateByPrimaryKeySelective(AppInfo record);

	int updateByPrimaryKey(AppInfo record);
}