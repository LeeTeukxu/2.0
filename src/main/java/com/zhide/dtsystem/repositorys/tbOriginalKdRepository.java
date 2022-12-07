package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbOriginalKd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface tbOriginalKdRepository extends  JpaRepository<tbOriginalKd,Integer>  {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET PackageContent=:PackageContent," +
            "PackageStatus=:PackageStatus," +
            "PackageNum=:PackageNum," +
            "Receiver=:Receiver," +
            "Postcode=:Postcode," +
            "Address=:Address," +
            "CourierCompany=:CourierCompany," +
            "PostalCode=:PostalCode," +
            "Phone=:Phone," +
            "ExpressNotes=:ExpressNotes WHERE Coding=:Coding",nativeQuery = true)
    int Update(@Param("PackageContent") String PackageContent,
               @Param("PackageStatus") Integer PackageStatus,
               @Param("PackageNum") String PackageNum,
               @Param("Receiver") Integer Receiver,
               @Param("Postcode") String Postcode,
               @Param("Address") String Address,
               @Param("CourierCompany") String CourierCompany,
               @Param("PostalCode") String PostalCode,
               @Param("Phone") String Phone,
               @Param("ExpressNotes") String ExpressNotes,
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
    @Query(value = "UPDATE tbOriginalExpress SET PickUp=:PickUp," +
            "PickUpTime=:PickUpTime,OriginalStates=2 WHERE PickUpNumber=:PickUpNumber",nativeQuery = true)
    int UpdatePickUp(@Param("PickUp") String PickUp,
                     @Param("PickUpTime") Date PickUpTime,
                     @Param("PickUpNumber") String PickUpNumber);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalKd SET PackageStatus=2," +
            "DeliveryTime=:DeliveryTime," +
            "Render=:Render WHERE PackageNum=:PackageNum UPDATE tbOriginalExpress SET OriginalStates=4 WHERE PackageNum=:PackageNum",nativeQuery = true)
    int UpdatePackageInfo(@Param("DeliveryTime") Date DeliveryTime,
                          @Param("Render") String Render,
                          @Param("PackageNum") String PackageNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalKd SET PackageStatus=1," +
            "DeliveryTime=NULL," +
            "Render=NULL WHERE PackageNum=:PackageNum UPDATE tbOriginalExpress SET OriginalStates=3 WHERE Coding IN (SELECT Coding FROM tbOriginalExpress WHERE PackageNum=:PackageNum)",nativeQuery = true)
    int ExpressNot(@Param("PackageNum") String PackageNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbOriginalExpress SET OriginalStates=0,PackageNum=NULL WHERE Coding=:Coding",nativeQuery = true)
    int OriginalKdNo(@Param("Coding") String Coding);

    @Transactional
    @Modifying
    @Query(value = "DELETE tbOriginalKd WHERE PackageNum=:PackageNum",nativeQuery = true)
    int delOriginalKd(@Param("PackageNum") String PackageNum);
}
