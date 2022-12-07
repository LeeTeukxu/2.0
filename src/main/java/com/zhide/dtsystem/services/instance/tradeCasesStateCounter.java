package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.repositorys.tradeCasesRepository;
import com.zhide.dtsystem.services.define.IStateCounter;
import com.zhide.dtsystem.services.define.ITradeCasesCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class tradeCasesStateCounter {
    @Autowired
    tradeCasesRepository tradeCasesRepository;
    @Autowired
    ITradeCasesCounterService casesRep;
    List<caseCounterInfo> dataSource = new ArrayList<>();
    List<caseCounterInfo> ywSource = new ArrayList<>();

    private void prepareData() {
//        List<tradeCases> dd = tradeCasesRepository.findAll();
//        dataSource = dd.stream().map(f -> {
//            caseCounterInfo info = new caseCounterInfo();
//            info.setCasesid(f.getCasesid());
//            info.setAuditMan(f.getAuditMan());
//            info.setCreateMan(f.getCreateMan());
//            info.setState(f.getState());
//            info.setTechMan(f.getTechMan());
//            info.setTechManager(f.getTechManager());
//            info.setAuditManager(f.getAuditManager());
//            info.setCreateManager(f.getCreateManager());
//            return info;
//        }).collect(toList());
        dataSource = casesRep.getTradeCases();
        ywSource = casesRep.getSub();
    }

    public Map<String, Integer> getResult(Integer userId, String roleName) {
        prepareData();
        List<IStateCounter> all = Arrays.asList(
                new One(dataSource, userId, roleName),
                new Two(dataSource, userId, roleName),
                new Three(dataSource, userId, roleName),
                new Four(dataSource, userId, roleName),
                new Five(ywSource, userId, roleName),
                new Six(ywSource, userId, roleName),
                new Seven(ywSource, userId, roleName),
                new Eight(dataSource, userId, roleName),
                new Nine(dataSource, userId, roleName),
                new FourtyFive(dataSource, userId, roleName)
        );
        List<String> States = Arrays.asList(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "45"
        );
        Map<String, Integer> result = new HashMap<>();
        for (Integer i = 0; i < States.size(); i++) {
            Integer X = Integer.parseInt(States.get(i));
            Optional<IStateCounter> FindCounter = all.stream().filter(f -> f.accept(X)).findFirst();
            if (FindCounter.isPresent()) {
//                IStateCounter Counter = FindCounter.get();
//                if (roleName.equals("系统管理员") || roleName.equals("财务人员") || roleName.indexOf("流程") > -1 ) {
                    List<caseCounterInfo> finds = dataSource;
                    if (X < 5) {
                        finds = dataSource.stream()
                                .filter(f -> f.getState() == X)
                                .collect(toList());
                    } else if (X == 45) {
                        finds = dataSource.stream().filter(f -> f.getState() == 4 || f.getState() == 5).collect(toList());
                    } else if (X > 4 && X < 7){
                        finds = ywSource.stream().filter(f -> f.getState() == X).collect(toList());
                    } else if (X == 7){
                        finds = ywSource.stream().filter(f -> f.getState() > 0).collect(toList());
                    }
                    result.put(Integer.toString(X), finds.size());
//                } else result.put(Integer.toString(X), Counter.getNumber());
            }
        }
        return result;
    }

    public int DefaultFilter(String roleName, int userId, List<caseCounterInfo> all) {
        if (roleName.equals("流程人员")) {
            return IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId)
                    .count());
        } else if (roleName.equals("流程部门经理")) {
            return all.stream()
                    .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                    .collect(toList()).size();
        } else if (roleName.equals("技术人员")) {
            return all.stream()
                    .filter(f -> ListUtils.parse(f.getTechMan(), Integer.class).contains(userId))
                    .collect(toList()).size();
        } else if (roleName.equals("技术部门经理")) {
            return all.stream()
                    .filter(f -> ListUtils.parse(f.getTechManager(), Integer.class).contains(userId))
                    .collect(toList()).size();
        } else {
            return all.stream()
                    .filter(f -> (f.getCreateMan() == userId) ||
                            ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                    .collect(toList()).size();
        }
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
            Integer Num = all.stream()
                    .filter(f -> f.getCreateMan() == userId)
                    .collect(toList()).size();
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
            if (roleName.equals("流程人员") || roleName.equals("流程部门经理")) {
                return all.size();
            } else {
                return all.stream()
                        .filter(f -> f.getCreateMan() == userId ||
                                ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
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
        public Integer getNumber() {
            if (roleName.equals("流程人员")) {
                return IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId).count());
            } else if (roleName.equals("流程部门经理")) {
                return all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            } else if (roleName.equals("技术人员") || roleName.equals("技术部门经理")) {
                return 0;
            } else {
                return all.stream().filter(f -> (f.getCreateMan() == userId) || ListUtils.parse(f.getCreateManager(),
                        Integer.class).contains(userId))
                        .collect(toList()).size();
            }
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
            if (roleName.equals("流程人员")) {
                return IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId).count());
            } else if (roleName.equals("流程部门经理")) {
                return all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            } else {
                return all.stream()
                        .filter(f -> (f.getCreateMan() == userId) ||
                                ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            }
        }
    }

    /*接单未完成*/
    class Five implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Five(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 5).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 5;
        }

        @Override
        public Integer getNumber() {
            if (roleName.equals("流程人员")) {
                return IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId)
                        .count());
            } else if (roleName.equals("流程部门经理")) {
                return all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            } else if (roleName.equals("技术人员")) {
                return all.size();
            } else if (roleName.equals("技术部门经理")) {
                return all.size();
            } else {
                return all.stream()
                        .filter(f -> (f.getCreateMan() == userId) ||
                                ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            }
        }
    }

    /*接接完成*/
    class Six implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Six(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 6).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 6;
        }

        @Override
        public Integer getNumber() {
            return DefaultFilter(roleName, userId, all);
        }
    }

    /*未完结*/
    class Seven implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Seven(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() > 4).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 7;
        }

        @Override
        public Integer getNumber() {
            if (roleName.equals("流程人员")) {
                List<caseCounterInfo> bases = all.stream().filter(f -> f.getAuditMan() == userId).collect(toList());
                List<String> ids = bases.stream().map(f -> f.getCasesid()).collect(toList());
                List<caseCounterInfo> ands = all.stream().filter(f -> f.getState() == 2).filter(f -> ids.contains(f
                        .getCasesid()) == false).collect(toList());
                return bases.size() + ands.size();
            } else if (roleName.equals("流程部门经理")) {
                List<caseCounterInfo> bases = all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList());
                List<String> ids = bases.stream().map(f -> f.getCasesid()).collect(toList());
                List<caseCounterInfo> ands = all.stream().filter(f -> f.getState() == 2).filter(f -> ids.contains(f
                        .getCasesid()) == false).collect(toList());
                return bases.size() + ands.size();
            } else if (roleName.equals("技术人员")) {
                List<caseCounterInfo> bases = all.stream()
                        .filter(f -> ListUtils.parse(f.getTechMan(), Integer.class).contains(userId))
                        .collect(toList());
                List<String> ids = bases.stream().map(f -> f.getCasesid()).distinct().collect(toList());
                List<caseCounterInfo> ands = all.stream().filter(f -> f.getState() == 4).filter(f -> ids.contains(f
                        .getCasesid()) == false).collect(toList());
                return bases.size() + ands.size();
            } else if (roleName.equals("技术部门经理")) {
                List<caseCounterInfo> bases = all.stream()
                        .filter(f -> ListUtils.parse(f.getTechManager(), Integer.class).contains(userId))
                        .collect(toList());
                List<String> ids = bases.stream().map(f -> f.getCasesid()).distinct().collect(toList());
                List<caseCounterInfo> ands = all.stream().filter(f -> f.getState() == 4).filter(f -> ids.contains(f
                        .getCasesid()) == false).collect(toList());
                return bases.size() + ands.size();
            } else {
                return all.stream()
                        .filter(f -> (f.getCreateMan() == userId) ||
                                ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            }
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
        }

        public boolean accept(Integer Index) {
            return Index == 8;
        }

        @Override
        public Integer getNumber() {
            return DefaultFilter(roleName, userId, all);
        }
    }

    /*全部*/
    class Nine implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public Nine(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 9;
        }

        @Override
        public Integer getNumber() {
            Integer Num = 0;
            if (roleName.equals("流程人员")) {
                Num = IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId || f.getState() == 2)
                        .count());
            } else if (roleName.equals("流程部门经理")) {
                Num = all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId) || f
                                .getState() == 2)
                        .collect(toList()).size();
            } else if (roleName.equals("技术人员")) {
                Num = all.stream()
                        .filter(f -> ListUtils.parse(f.getTechMan(), Integer.class).contains(userId) || f.getState()
                                == 4 || f.getState() == 5)
                        .collect(toList()).size();
            } else if (roleName.equals("技术部门经理")) {
                Num = all.stream()
                        .filter(f -> ListUtils.parse(f.getTechManager(), Integer.class).contains(userId) || f
                                .getState() == 4 || f.getState() == 5)
                        .collect(toList()).size();
            } else {
                Num = all.stream()
                        .filter(f -> (f.getCreateMan() == userId) ||
                                ListUtils.parse(f.getCreateManager(), Integer
                                        .class).contains(userId))
                        .collect(toList()).size();
            }
            return Num;
        }
    }

    /*专利已申报*/
    class FourtyFive implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public FourtyFive(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 4 || f.getState() == 5).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 45;
        }

        @Override
        public Integer getNumber() {

            if (roleName.equals("流程人员")) {
                return IntegerUtils.parseInt(all.stream().filter(f -> f.getAuditMan() == userId)
                        .count());
            } else if (roleName.equals("流程部门经理")) {
                return all.stream()
                        .filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            } else if (roleName.equals("技术人员")) {
                return all.size();
            } else if (roleName.equals("技术部门经理")) {
                return all.size();
            } else {
                return all.stream()
                        .filter(f -> (f.getCreateMan() == userId) ||
                                ListUtils.parse(f.getCreateManager(), Integer.class).contains(userId))
                        .collect(toList()).size();
            }
        }
    }
}
