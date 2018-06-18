package cn.appsys.dao;

import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.AppVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppVersionMapper {
	
	public int updateAppVersionById(@Param("id") Integer id);
	
	public List<AppVersion> getAppVersionList(@Param("appId")Integer appId);
	
	public List<AppVersion> getAppVersionListFlush(@Param("appId")Integer appId);
	
    int countByExample(AppVersionExample example);

    int deleteByExample(AppVersionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    List<AppVersion> selectByExample(AppVersionExample example);

    AppVersion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    int updateByExample(@Param("record") AppVersion record, @Param("example") AppVersionExample example);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
}