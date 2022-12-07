package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.pantentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface pantentInfoRepository extends JpaRepository<pantentInfo, String> {
    @Query(value = "Select * from PantentInfo Where Len(rtrim(ltrim(isnull(NeiBuBH,''))))<2 and " +
            "len(isnull(UploadPath,''))>10 And isnull(CanParse,1)=1", nativeQuery = true)
    List<pantentInfo> findAllEmptyNeiBubh();

    List<pantentInfo> findAllByShenqingbh(String shenqingbh);

    List<pantentInfo> findAllByShenqinghIn(String[] codes);

    List<pantentInfo> findAllByShenqinghIn(List<String> shenqingbhs);

    @Query(value = "Select Top 20 * from pantentInfo Where Shenqingrxm is null And  len(isnull(UploadPath,''))>5 And " +
            "isnull(CanFixed,1)=1", nativeQuery = true)
    List<pantentInfo> findAllNeedFixedPantentInfo();
    @Query(value="Select Top 20 * from pantentInfo  Where Shenqinglx is null And  len(isnull(UploadPath,''))>5",
            nativeQuery = true)
    List<pantentInfo> findAllByShenqinglxIsNull();

    @Query(value = "Select top 1000 * from pantentInfo  Where len(replace(isnull(neibubh,''),' ',''))>3 And isnull(NBFixed,0)=0 and SHENQINGH not in(Select Shenqingh from DecodeTaskError)", nativeQuery = true)
    List<pantentInfo> findNeedDecodeNBBHPantents();

    Optional<pantentInfo> findByShenqingh(String shenqingh);

    @Transactional
    @Modifying
    @Query(value = "UPDATE YearFeeList SET JkStatus=:JkStatus WHERE id=:id", nativeQuery = true)
    int UpdateYearJkStatus(@Param("JkStatus") Integer JkStatus,
            @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ApplyFeeList SET JkStatus=:JkStatus WHERE id=:id", nativeQuery = true)
    int UpdateApplyJkStatus(@Param("JkStatus") Integer JkStatus,
            @Param("id") Integer id);

    @Query(value="Select * from PantentInfo Where Isnull(NBFixed,0)=0 And Shenqingh in(Select Distinct Shenqingh from DecodeTaskError)",nativeQuery = true)
    List<pantentInfo> getAllUpdateNBBHItems();

    /*
    * 内部编号解析出现重复的专利。需要找出来。再解析一次。
    */
    @Query(value="Select * from PantentInfo Where Shenqingh in(Select SHENQINGH from (select Shenqingh,UserType,Count(0) as Num from PatentInfoPermission group by SHENQINGH,UserType ) t  Where Num>1)",nativeQuery = true)
    List<pantentInfo> getErrorNeiBuBHItems();
    List<pantentInfo> getAllByNeibubhLike(String name);

    @Query(value="Select Shenqingh from PantentInfo a  left  join lawstatus b on a.ANJIANYWZT=b.Name Where isnull(b" +
            ".Status,0)!=4 order by LASTUPDATETIME  offset (?1)*100 rows fetch next 100 rows only",nativeQuery = true)
    List<String> getAllCodes(@Param("pageIndex")Integer pageIndex);
    @Query(value="Select Shenqingh from PantentInfo",nativeQuery = true)
    List<String>getAllCodes();
    @Query(value="Select Shenqingh from PantentInfo Where Shenqinglx is null",nativeQuery = true)
    List<String> getAllCodesShenqinglxIsNull();
    @Query(value="Select Shenqingh from PantentInfo Where anjianywzt is null",nativeQuery = true)
    List<String> getAllCodesAnjianywztIsNull();
    @Query(value="Select Shenqingh from PantentInfo Where lastupdatetime is null",nativeQuery = true)
    List<String> getAllCodesLastupdateTimeIsNull();
}
