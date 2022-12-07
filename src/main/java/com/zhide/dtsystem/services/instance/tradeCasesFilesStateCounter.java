package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.mapper.TradeCasesCommitBrowseMapper;
import com.zhide.dtsystem.models.caseCounterInfo;
import com.zhide.dtsystem.services.define.IStateCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Component
public class tradeCasesFilesStateCounter {
    @Autowired
    TradeCasesCommitBrowseMapper tradeCasesCommitBrowseMapper;
    List<caseCounterInfo> dataSource = new ArrayList<>();

    private void prepareData() {
        dataSource = tradeCasesCommitBrowseMapper.getAll();
    }

    public Map<String, Integer> getResult(Integer userId, String roleName) {
        prepareData();
        List<IStateCounter> all = Arrays.asList(
                new SixtyOne(dataSource, userId, roleName),
                new SixtyTwo(dataSource, userId, roleName),
                new SixtyThree(dataSource, userId, roleName),
                new SixtyFour(dataSource, userId, roleName)
        );
        List<String> States = Arrays.asList("61", "62", "63", "64");
        Map<String, Integer> result = new HashMap<>();
        for (Integer i = 0; i < States.size(); i++) {
            Integer X = Integer.parseInt(States.get(i));
            Optional<IStateCounter> FindCounter = all.stream().filter(f -> f.accept(X)).findFirst();
            if (FindCounter.isPresent()) {
                IStateCounter Counter = FindCounter.get();
                if (roleName.equals("系统管理员") || roleName.equals("财务人员")) {
                    List<caseCounterInfo> finds = dataSource.stream().filter(f -> f.getState() == X - 60).collect(toList
                            ());
                    result.put(Integer.toString(X), finds.size());
                } else result.put(Integer.toString(X), Counter.getNumber());
            }
        }
        return result;
    }

    private Integer GetDefaultNumber(List<caseCounterInfo> all, String roleName, Integer userId) {
        List<caseCounterInfo> res = null;
        switch (roleName) {
            case "技术人员": {
                res = all.stream().filter(f -> ListUtils.parse(f.getTechMan(), Integer.class).contains(userId)).collect
                        (toList());
                break;
            }
            case "技术部门经理": {
                res = all.stream().filter(f -> ListUtils.parse(f.getTechManager(), Integer.class).contains(userId)).collect
                        (toList());
                break;
            }
            case "流程人员": {
                res = all.stream().filter(f -> f.getAuditMan() == userId).collect(toList());
                break;
            }
            case "流程部门经理": {
                res = all.stream().filter(f -> ListUtils.parse(f.getAuditManager(), Integer.class).contains(userId))
                        .collect(toList());
                break;
            }
            default: {
                res = all.stream().filter(f -> f.getCreateMan() == userId || ListUtils.parse(f.getCreateManager(),
                        Integer.class).contains(userId)).collect(toList());
                break;
            }
        }
        return res.size();

    }

    /*技术文件待审核*/
    class SixtyOne implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyOne(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 1).collect(toList());
            userId = userid;
            roleName = rolename;

        }

        public boolean accept(Integer Index) {
            return Index == 61;
        }

        @Override
        public Integer getNumber() {
            return GetDefaultNumber(all, roleName, userId);
        }
    }

    /*交单待审核*/
    class SixtyTwo implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyTwo(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 2).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 62;
        }

        @Override
        public Integer getNumber() {
            return GetDefaultNumber(all, roleName, userId);
        }
    }

    /*拒绝交单*/
    class SixtyThree implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyThree(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 3).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 63;
        }

        @Override
        public Integer getNumber() {
            return GetDefaultNumber(all, roleName, userId);
        }
    }

    /*同意交单*/
    class SixtyFour implements IStateCounter {
        private List<caseCounterInfo> all = null;
        private Integer userId = 0;
        private String roleName = "";

        public SixtyFour(List<caseCounterInfo> my, Integer userid, String rolename) {
            all = my.stream().filter(f -> f.getState() == 4).collect(toList());
            userId = userid;
            roleName = rolename;
        }

        public boolean accept(Integer Index) {
            return Index == 64;
        }

        @Override
        public Integer getNumber() {
            return GetDefaultNumber(all, roleName, userId);
        }
    }
}
