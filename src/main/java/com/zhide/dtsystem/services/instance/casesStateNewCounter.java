package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.models.CacheableTtl;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.models.casesSubUser;
import com.zhide.dtsystem.models.casesUser;
import com.zhide.dtsystem.services.ManagerUtils;
import com.zhide.dtsystem.services.UserPermission;
import com.zhide.dtsystem.services.define.ICasesCounterService;
import com.zhide.dtsystem.services.define.IStateCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class casesStateNewCounter {
    @Autowired
    ICasesCounterService casesRep;
    @Autowired
    UserPermission userPermission;
    @Autowired
    ManagerUtils managerUtils;

    List<caseCounterInfo> mainSource = new ArrayList<>();
    List<caseCounterInfo> subSource = new ArrayList<>();
    List<casesUser> allUsers = new ArrayList<>();
    List<casesSubUser> allSubUsers = new ArrayList();
    Logger logger = LoggerFactory.getLogger(casesStateNewCounter.class);

    private void prepareData() {
        Long begin = System.currentTimeMillis();
        mainSource = casesRep.getMain();
        subSource = casesRep.getSub();
        allUsers = casesRep.getAllUser();
        allSubUsers = casesRep.getAllSubUser();
        Long end = System.currentTimeMillis();
        logger.info("Prepare data used times:" + (end - begin));
    }

    @CacheableTtl(value = "getCasesStateNewCounter", keyGenerator = "CompanyKeyGenerator", ttl = 20, cacheResolver =
            "ttlCacheResolver")
    public Map<String, String> getResult(Integer userId, String roleName) {
        prepareData();
        List<IStateCounter> all = Arrays.asList(
                new One(mainSource, userId, roleName),
                new Two(mainSource, userId, roleName),
                new Three(mainSource, userId, roleName),
                new Four(mainSource, userId, roleName),
                new Seven(mainSource, userId, roleName),
                new Eight(mainSource, userId, roleName),
                new Nine(mainSource, userId, roleName),
                new Fifty(subSource, userId, roleName),
                new FiftyOne(subSource, userId, roleName),
                new FiftyTwo(subSource, userId, roleName),
                new FiftyThree(subSource, userId, roleName),
                new FiftyFour(subSource, userId, roleName),
                new FiftyFive(subSource, userId, roleName),
                new FiftySix(subSource, userId, roleName),
                new SixtyOne(subSource, userId, roleName),
                new SixtyTwo(subSource, userId, roleName),
                new SixtyThree(subSource, userId, roleName),
                new SixtyFour(subSource, userId, roleName),
                new Seventy(subSource, userId, roleName),
                new Ninety(subSource, userId, roleName)
        );
        List<String> States = Arrays.asList(
                "1", "2", "3", "4", "7", "8", "9", "50", "51", "52", "53", "54", "55", "56", "61", "62", "63", "64",
                "70", "90"
        );
        Map<String, String> result = new HashMap<>();
        for (Integer i = 0; i < States.size(); i++) {
            Integer X = Integer.parseInt(States.get(i));
            Optional<IStateCounter> FindCounter = all.stream().filter(f -> f.accept(X)).findFirst();
            if (FindCounter.isPresent()) {
                IStateCounter Counter = FindCounter.get();
                Integer Y = Counter.getNumber();
                String Key = Integer.toString(X);
                if (Y != null) {
                    result.put(Key, Integer.toString(Y));
                } else result.put(Key, "0");
            }
        }
        return result;
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-07 23:43
     * @Params:[userId, cs]
     * Description:看第一笔业务中的人员列表中是否有当前用户。如果有。则代表当前用户曾经与当前业务有关系。可以看。
     * 至于能否操作主要还是要看权限。只要分配了特定的按钮权限，就可以操作特定记录。
     */
    public int filterByUserID(String roleName, int userId, List<caseCounterInfo> cs) {
        if (roleName.equals("系统管理员") ||
                roleName.indexOf("财务") > -1 ||
                roleName.indexOf("流程") > -1) {
            return cs.size();
        }
        int Num = 0;
        for (int i = 0; i < cs.size(); i++) {
            caseCounterInfo c = cs.get(i);
            String MID = c.getCreateManager();
            List<Integer> Users = ListUtils.parse(MID, Integer.class);
            Users.add(c.getCreateMan());
            if (Users.contains(userId)) Num++;
        }
        return Num;
    }

    public int filterBySubUserID(String roleName, int userId, List<caseCounterInfo> cs) {
        if (roleName.equals("系统管理员") ||
                roleName.indexOf("财务") > -1 ||
                roleName.indexOf("流程") > -1) {
            return cs.size();
        }
        List<Integer> users = managerUtils.getAllUsers(userId);
        List<String> SubIDS = allSubUsers.stream()
                .filter(f -> users.contains(f.getUserId()))
                .map(f -> f.getSubId())
                .distinct()
                .collect(toList());
        return Math.toIntExact(cs.stream().filter(f -> SubIDS.contains(f.getSubid())).count());
    }

    /*交单保存*/
    class One implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public One(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 1).collect(toList());
            userId = userid;
            roleName = rolename;

        }

        public boolean accept(Integer Index) {
            return Index == 1;
        }

        @Override
        public Integer getNumber() {
            int Num=Math.toIntExact(all.stream().filter(f->f.getCreateMan()==userId).count());
            return Num;
        }
    }

    /*交单待审核*/
    class Two implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Two(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 2).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 2;
        }

        @Override
        public Integer getNumber() {
            if (userPermission.Exists(roleName, "AuditCases")) {
                return all.size();
            } else {
                return filterByUserID(roleName, userId, all);
            }
        }
    }

    /*不同意交单*/
    class Three implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Three(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 3).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 3;
        }

        @Override
        public Integer getNumber(){
            int Num=Math.toIntExact(all.stream().filter(f->f.getCreateMan()==userId).count());
            return Num;
        }
    }

    /*同意交单*/
    class Four implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Four(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 4).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 4;
        }

        @Override
        public Integer getNumber() {
            return filterByUserID(roleName, userId, all);
        }
    }

    /*未确定代理师*/
    class Fifty implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Fifty(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 50).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 50;
        }

        @Override
        public Integer getNumber() {
            if (roleName.indexOf("技术") > -1) {
                if (userPermission.Exists(roleName, "AcceptTech")) {
                    return all.size();
                } else return 0;
            } else {
                if (userPermission.Exists(roleName, "AcceptTech")) {
                    return filterByUserID(roleName, userId, all);
                } else return 0;
            }
        }
    }

    /*已确定代理师*/
    class FiftyOne implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftyOne(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 51).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 51;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*待核稿*/
    class FiftyTwo implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftyTwo(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 52).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 52;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*核稿驳回*/
    class FiftyThree implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftyThree(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 53).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 53;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*核稿通过*/
    class FiftyFour implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftyFour(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 54).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 54;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*客户不定稿*/
    class FiftyFive implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftyFive(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 55).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 55;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*客户定稿*/
    class FiftySix implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FiftySix(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 56).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 56;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*流程申报-未申报*/
    class SixtyOne implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyOne(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 61).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 61;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    /*流程申报-未申报*/
    class SixtyTwo implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyTwo(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 62).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 62;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    class SixtyThree implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyThree(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 63).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 63;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    class SixtyFour implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyFour(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 64).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 64;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    class Seventy implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Seventy(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 70).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 70;
        }

        @Override
        public Integer getNumber() {
            return filterBySubUserID(roleName, userId, all);
        }
    }

    class Ninety implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Ninety(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my;
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 90;
        }

        @Override
        public Integer getNumber() {
            int A= filterBySubUserID(roleName, userId, all);
            List<caseCounterInfo> fives=all.stream().filter(f->f.getState()==50).collect(toList());
            int B=0;
            if (roleName.indexOf("技术") > -1) {
                if (userPermission.Exists(roleName, "AcceptTech")) {
                    B= fives.size();
                }
            } else {
                if (userPermission.Exists(roleName, "AcceptTech")) {
                    B= filterByUserID(roleName, userId, fives);
                }
            }
            return A+B;
        }
    }

    /*未完结*/
    class Seven implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Seven(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() < 8).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 7;
        }

        @Override
        public Integer getNumber() {
            return filterByUserID(roleName, userId, all);
        }
    }

    /*完结*/
    class Eight implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Eight(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 8).collect(toList());
            userId = userid;
            roleName = rolename;
            if (all == null) all = new ArrayList<>();
        }

        public boolean accept(Integer Index) {
            return Index == 8;
        }

        @Override
        public Integer getNumber() {
            return filterByUserID(roleName, userId, all);
        }
    }

    /*全部*/
    class Nine implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Nine(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() >= 1 && f.getState() <= 9).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 9;
        }

        @Override
        public Integer getNumber() {
            return filterByUserID(roleName, userId, all);
        }
    }
}
