package cn.appsys.dao;

import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DataDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DataDictionaryMapper {

	List<DataDictionary> getDictionarieListByTypeCode(String typeCode);

	int countByExample(DataDictionaryExample example);

	int deleteByExample(DataDictionaryExample example);

	int deleteByPrimaryKey(Long id);

	int insert(DataDictionary record);

	int insertSelective(DataDictionary record);

	List<DataDictionary> selectByExample(DataDictionaryExample example);

	DataDictionary selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") DataDictionary record,
			@Param("example") DataDictionaryExample example);

	int updateByExample(@Param("record") DataDictionary record,
			@Param("example") DataDictionaryExample example);

	int updateByPrimaryKeySelective(DataDictionary record);

	int updateByPrimaryKey(DataDictionary record);
}