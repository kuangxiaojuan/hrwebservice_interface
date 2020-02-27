package dhr.provider;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import dhr.provider.SqlMapperProvider;
import dhr.provider.vo.InsertVO;
import dhr.provider.vo.UpdateVO;

public interface SqlMapper {
	
	@InsertProvider(type = SqlMapperProvider.class, method= "excute")
	public Integer excute(InsertVO insertVO);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "insert")
	public Integer insert(InsertVO insertVO);
	
	@UpdateProvider(type = SqlMapperProvider.class, method= "update")
	public Integer update(UpdateVO updateVO);
	
	@UpdateProvider(type = SqlMapperProvider.class, method= "updateByConds")
	public Integer updateByConds(UpdateVO updateVO);
	
	@UpdateProvider(type = SqlMapperProvider.class, method= "delete")
	public Integer delete(UpdateVO updateVO);

	@UpdateProvider(type = SqlMapperProvider.class, method= "updateBatch")
	public Integer updateBatch(UpdateVO updateVO);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "insertBatchStatic")
	public Integer insertBatch(InsertVO insertVO);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "insertBatchDynamic")
	public Integer insertBatchReturnRows(InsertVO insertVO);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "deleteBatch")
	public Integer deleteBatch(UpdateVO updateVO);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "insertUsr")
	public String insertUsr(@Param("personMap") Map<String, Object> map);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "writePersonel")
	public Integer insertWritePersonel(@Param("personMap") Map<String, Object> personMap);
	
	//从App改到Usr表
	@InsertProvider(type = SqlMapperProvider.class, method= "writePersonelusr")
	public Integer insertwritePersonelusr(@Param("personMap") Map<String, Object> personMap);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "AppAZ3")
	public Integer insertAPP(@Param("personMap") Map<String, Object> personMap,@Param("tableName") String tableName,@Param("A0100") String A0100);
	
	@InsertProvider(type = SqlMapperProvider.class, method= "AppAZ3T")
	public Integer insertAPP2(@Param("personMap") Map<String, Object> personMap,@Param("tableName") String tableName,@Param("A0100") String A0100);
	
	
	@InsertProvider(type = SqlMapperProvider.class, method= "insertUsrList")
	public Integer insertUsrList(@Param("list") List<Map<String, Object>> list,@Param("tableName")String tableName,@Param("A0100")String A0100);
	
	//入职 usra01
//	@SelectProvider(type = SqlMapperProvider.class, method= "insertPerson")
//	public String insertPerson(@Param("list")List<Map<String, Object>> list);
}
