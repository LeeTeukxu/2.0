package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface tbClientRepository extends JpaRepository<tbClient, Integer> {
    tbClient findAllByClientID(Integer clientId);
    Optional<tbClient> findFirstByOrgCode(String OrgCode);
    Optional<tbClient> findFirstByName(String ClientName);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbClient SET signMan=:signMan,preSignMan=:preSignMan WHERE clientID=:clientID",
            nativeQuery = true)
    int SaveChangeKH(@Param("signMan") Integer SignMan,
            @Param("preSignMan") Integer preSignMan,
            @Param("clientID") Integer ClientID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE PatentInfoPermission SET USERID=:NOWXS WHERE SHENQINGH in(SELECT SHENQINGH FROM " +
            "PatentInfoPermission WHERE USERID=:CLIENTID AND USERTYPE='KH') AND USERTYPE='YW'", nativeQuery = true)
    int updatePatentInfoPermission(@Param("NOWXS") Integer NOWXS,
            @Param("CLIENTID") Integer CLIENTID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CASESPERMISSION SET MANID=:NOWXS WHERE CASEID in(SELECT CASEID FROM CASESPERMISSION WHERE " +
            "MANID=:CLIENTID AND TYPE='KH') AND TYPE='YW'", nativeQuery = true)
    int updateCASESPERMISSION(@Param("NOWXS") Integer NOWXS,
            @Param("CLIENTID") Integer CLIENTID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CASES set CREATEMAN=:NOWXS WHERE CASESID in(SELECT CASEID FROM CASESPERMISSION WHERE " +
            "TYPE='KH' OR TYPE='YW' AND MANID=:ClientID) AND CREATEMAN=:OLDXS", nativeQuery = true)
    int updateCASES(@Param("NOWXS") Integer NOWXS,
            @Param("ClientID") Integer ClientID,
            @Param("OLDXS") Integer OLDXS);
}
