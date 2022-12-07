package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbOriginalExpress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface tbOriginalExpressRepository extends  JpaRepository<tbOriginalExpress,Integer>  {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PackageNum=:PackageNum,PickUpNumber=NULL,OriginalStates=3 WHERE Coding=:Coding",nativeQuery = true)
    int Update(@Param("PackageNum") String PackageNum,
               @Param("Coding") String Coding);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PickUpNumber=:PickUpNumber," +
            "OriginalStates=:OriginalStates," +
            "PickUpApplicant=:PickUpApplicant," +
            "PickUpApplicationTime=:PickUpApplicationTime WHERE Coding=:Coding",nativeQuery = true)
    int UpdateYJZT(@Param("PickUpNumber") String PickUpNumber,
                   @Param("OriginalStates") String OriginalStates,
                   @Param("PickUpApplicant") Integer PickUpApplicant,
                   @Param("PickUpApplicationTime") Date PickUpApplicationTime,
                   @Param("Coding") String Coding);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbOriginalKd WHERE PackageNum=:PackageNum",nativeQuery = true)
    int DelOriginalKd(@Param("PackageNum") String PackageNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PackageNum=NULL WHERE PackageNum=:PackageNum",nativeQuery = true)
    int DelOfUpPackageNum(@Param("PackageNum") String PackageNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalKd SET PackageContent=:PackageContent WHERE PackageNum=:PackageNum",nativeQuery = true)
    int UpdatePackageContent(@Param("PackageContent") String PackageContent,
                             @Param("PackageNum") String PackageNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PickUp=:PickUp," +
            "PickUpTime=:PickUpTime,OriginalStates=2 WHERE PickUpNumber=:PickUpNumber",nativeQuery = true)
    int UpdatePickUp(@Param("PickUp") String PickUp,
                     @Param("PickUpTime") Date PickUpTime,
                     @Param("PickUpNumber") String PickUpNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET OriginalStates=0,PickUpNumber=NULL,PickUp=NULL,PickUpTime=NULL,PickUpApplicant=NULL,PickUpApplicationTime=NULL WHERE Coding=:Coding",nativeQuery = true)
    int PickUpNo(@Param("Coding") String Coding);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PickUp=NULL,PickUpTime=NULL,OriginalStates=1 WHERE PickUpNumber=:PickUpNumber",nativeQuery = true)
    int UpdatePickUpStatusForDZQ(@Param("PickUpNumber") String DrawNo);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET OriginalStates=:OriginalStates where PackageNum=:PackageNum",nativeQuery = true)
    int UpdateOriginalStates(@Param("OriginalStates") Integer OriginalStates,
                             @Param("PackageNum") String PackageNum);
}
