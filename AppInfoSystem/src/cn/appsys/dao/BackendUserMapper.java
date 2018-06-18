package cn.appsys.dao;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.BackendUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BackendUserMapper {

	BackendUser getLoginUser(@Param("userCode") String userCode,
			@Param("userPassword") String userPassword);

	int countByExample(BackendUserExample example);

	int deleteByExample(BackendUserExample example);

	int deleteByPrimaryKey(Long id);

	int insert(BackendUser record);

	int insertSelective(BackendUser record);

	List<BackendUser> selectByExample(BackendUserExample example);

	BackendUser selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") BackendUser record,
			@Param("example") BackendUserExample example);

	int updateByExample(@Param("record") BackendUser record,
			@Param("example") BackendUserExample example);

	int updateByPrimaryKeySelective(BackendUser record);

	int updateByPrimaryKey(BackendUser record);
}