package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.models.otherOfficeFee;
import com.zhide.dtsystem.repositorys.otherOfficeFeeListRepository;
import com.zhide.dtsystem.services.define.IStateCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class otherOfficeFeeCounter {
    @Autowired
    otherOfficeFeeListRepository otherOfficeFeeListRepository;
    List<otherOfficeFee> dataSource = new ArrayList<>();

    private void prepareData() {
        List<otherOfficeFee> dd = otherOfficeFeeListRepository.findAll();
        dataSource = dd.stream().map(f -> {
            otherOfficeFee info = new otherOfficeFee();
            info.setOtherOfficeStates(f.getOtherOfficeStates());
            return info;
        }).collect(Collectors.toList());
    }

    public Map<String, Integer> getResult() {
        prepareData();
        Map<String, Integer> result = new HashMap<>();
        if (dataSource.size() > 0) {
            List<IStateCounter> all = Arrays.asList(
                    new Zero(dataSource),
                    new One(dataSource),
                    new Two(dataSource),
                    new Three(dataSource),
                    new Four(dataSource)
            );
            List<String> States = Arrays.asList("0", "1", "2", "3", "4");

            for (Integer i = 0; i < States.size(); i++) {
                Integer X = Integer.parseInt(States.get(i));
                Optional<IStateCounter> FindCounter = all.stream().filter(f -> f.accept(X)).findFirst();
                if (FindCounter.isPresent()) {
                    IStateCounter Counter = FindCounter.get();
                    if (X == 0) {
                        List<otherOfficeFee> find = dataSource;
                        result.put(Integer.toString(X), find.size());
                    } else {
                        result.put(Integer.toString(X), Counter.getNumber());
                    }
                }
            }
        } else {
            List<String> States = Arrays.asList("0", "1", "2", "3", "4");
            for (Integer i = 0; i < States.size(); i++) {
                Integer X = Integer.parseInt(States.get(i));
                result.put(Integer.toString(X), 0);
            }
        }
        return result;
    }

    class Zero implements IStateCounter {
        private List<otherOfficeFee> all = null;

        public Zero(List<otherOfficeFee> my) {
            all = my.stream().collect(toList());
        }

        public boolean accept(Integer Index) {
            return Index == 0;
        }

        @Override
        public Integer getNumber() {
            Integer Num = all.stream().filter(f -> f.getOtherOfficeStates() > 0).collect(toList()).size();
            return Num;
        }
    }

    class One implements IStateCounter {
        private List<otherOfficeFee> all = null;

        public One(List<otherOfficeFee> my) {
            all = my.stream().filter(f -> f.getOtherOfficeStates() == 1).collect(Collectors.toList());
        }

        public boolean accept(Integer Index) {
            return Index == 1;
        }

        @Override
        public Integer getNumber() {
            Integer Num = all.stream().filter(f -> f.getOtherOfficeStates() == 1).collect(toList()).size();
            return Num;
        }
    }

    class Two implements IStateCounter {
        private List<otherOfficeFee> all = null;

        public Two(List<otherOfficeFee> my) {
            all = my.stream().filter(f -> f.getOtherOfficeStates() == 2).collect(Collectors.toList());
        }

        public boolean accept(Integer Index) {
            return Index == 2;
        }

        @Override
        public Integer getNumber() {
            Integer Num = all.stream().filter(f -> f.getOtherOfficeStates() == 2).collect(toList()).size();
            return Num;
        }
    }

    class Three implements IStateCounter {
        private List<otherOfficeFee> all = null;

        public Three(List<otherOfficeFee> my) {
            all = my.stream().filter(f -> f.getOtherOfficeStates() == 3).collect(Collectors.toList());
        }

        public boolean accept(Integer Index) {
            return Index == 3;
        }

        @Override
        public Integer getNumber() {
            Integer Num = all.stream().filter(f -> f.getOtherOfficeStates() == 3).collect(toList()).size();
            return Num;
        }
    }

    class Four implements IStateCounter {
        private List<otherOfficeFee> all = null;

        public Four(List<otherOfficeFee> my) {
            all = my.stream().filter(f -> f.getOtherOfficeStates() == 4).collect(Collectors.toList());
        }

        public boolean accept(Integer Index) {
            return Index == 4;
        }

        @Override
        public Integer getNumber() {
            Integer Num = all.stream().filter(f -> f.getOtherOfficeStates() == 4).collect(toList()).size();
            return Num;
        }
    }
}
