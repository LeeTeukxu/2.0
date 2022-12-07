package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.DepDataHelper;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ItbEmployeeService;
import com.zhide.dtsystem.services.define.ItbLoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class tbEmployeeServiceImpl implements ItbEmployeeService {

    @Autowired
    private v_tbEmployeeRepository v_tbEmployeeRepository;
    @Autowired
    private tbEmployeeRepository employeeRepository;
    @Autowired
    private tbLoginUserRepository loginUserRepository;
    @Autowired
    private ItbLoginUserService loginUserService;
    @Autowired
    private AllUserListMapper userListMapper;
    @Autowired
    StringRedisTemplate redisRep;

    @Autowired
    casesUserRepository caseUserRep;
    @Autowired
    casesSubUserRepository caseSubUserRep;
    @Autowired
    caseHighUserRepository caseHighUserRep;
    @Autowired
    caseHighSubUserRepository caseHighSubUserRep;
    @Autowired
    RedisUtils redisUtils;

    Logger logger = LoggerFactory.getLogger(tbEmployeeServiceImpl.class);

    @Override
    public tbEmployee getById(Integer empId) {
        return employeeRepository.getOne(empId);
    }

    @Override
    public List<tbEmployee> getAllByDepId(Integer depId) {
        return employeeRepository.findAllByDepId(depId);
    }

    @Override
    public Page<v_tbEmployee> getPage(Integer depId, Pageable pageable) {
        List<Integer> ids = DepDataHelper.getAllChildrenId(depId);
        return v_tbEmployeeRepository.findAllByDepIdIn(ids, pageable);
    }

    @Override
    public Page<v_tbEmployee> getPage(String empName, Pageable pageable) {
        return v_tbEmployeeRepository.findAllByEmpNameLikeOrLoginCodeLike(empName,empName,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeAndUser saveAll(tbEmployee employee, tbLoginUser loginUser) throws Exception {
        EmployeeAndUser result = new EmployeeAndUser();
        try {
            if (employee.getEmpId() != null) {
                Optional<tbEmployee> fEmployee = employeeRepository.findById(employee.getEmpId());
                if (fEmployee.isPresent()) {
                    EntityHelper.copyObject(employee, fEmployee.get());
                }
            } else {
                String empName = employee.getEmpName();
                List<tbEmployee> finds = employeeRepository.findAllByEmpName(empName);
                if (finds.size() > 0) {
                    throw new Exception(empName + "已存在，不允许重名!");
                }

            }
            tbEmployee lastEmp = employeeRepository.save(employee);

            loginUser.setDepId(lastEmp.getDepId());
            loginUser.setEmpId(lastEmp.getEmpId());
            tbLoginUser lastUser = loginUserService.save(loginUser);

            LoginUserInfo info = CompanyContext.get();
            userListMapper.SyncUser(info.getCompanyId(), lastUser.getLoginCode());
            result.setEmployee(lastEmp);
            result.setLoginUser(lastUser);
            RemoveAllKeys();
        } catch (Exception ax) {
            ax.printStackTrace();
            throw ax;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeAll(List<Integer> userId) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (Info == null) throw new Exception("登录失败，请登录后再执行操作!");
        String CompanyID = Info.getCompanyId();
        for (int i = 0; i < userId.size(); i++) {
            tbLoginUser user = loginUserRepository.getOne(userId.get(i));
            if (user != null) {
                int empId = user.getEmpId();
                Optional<tbEmployee> txs = employeeRepository.findById(empId);
                if (txs.isPresent()) {
                    tbEmployee emp = txs.get();
                    List<casesUser> mainUser = caseUserRep.findAllByUserId(user.getUserId());
                    if (mainUser.size() > 0) throw new Exception("【" + emp.getEmpName() + "】在专利交单中存在引用，无法删除!");

                    List<casesSubUser> subUser = caseSubUserRep.findAllByUserId(user.getUserId());
                    if (subUser.size() > 0) throw new Exception("【" + emp.getEmpName() + "】在专利交单中存在引用，无法删除!");

                    List<caseHighUser> mainUser1 = caseHighUserRep.findAllByUserId(user.getUserId());
                    if (mainUser.size() > 0) throw new Exception("【" + emp.getEmpName() + "】在高企交单中存在引用，无法删除!");

                    List<caseHighSubUser> subUser1 = caseHighSubUserRep.findAllByUserId(user.getUserId());
                    if (subUser.size() > 0) throw new Exception("【" + emp.getEmpName() + "】在高企交单中存在引用，无法删除!");

                    loginUserRepository.deleteById(userId.get(i));
                    employeeRepository.deleteById(empId);
                    userListMapper.deleteUserByAccount(user.getLoginCode(), Info.getCompanyId());
                    logger.info(Info.getUserName() + "删除了:" + user.getLoginCode() + ":【" + emp.getEmpName() + "】");
                }
            }
        }
        RemoveAllKeys();
        return true;
    }

    private void RemoveAllKeys() {
        redisUtils.clearAll("User");
    }
}
