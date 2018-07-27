package com.bootdo.clouddoadmin.controller;

import com.bootdo.clouddoadmin.domain.DeptDO;
import com.bootdo.clouddoadmin.domain.Tree;
import com.bootdo.clouddoadmin.service.DeptService;
import com.bootdo.clouddocommon.utils.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
	private String prefix = "system/dept";
	@Autowired
	private DeptService sysDeptService;

	@GetMapping()
	@RequiresPermissions("system:sysDept:sysDept")
	String dept() {
		return prefix + "/dept";
	}

	@ResponseBody
	@GetMapping("/list")
	public List<DeptDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<DeptDO> sysDeptList = sysDeptService.list(query);
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:sysDept:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", sysDeptService.get(pId).getName());
		}
		return  prefix + "/add";
	}

//	@GetMapping("/edit/{deptId}")
//	@RequiresPermissions("system:sysDept:edit")
//	String edit(@PathVariable("deptId") Long deptId, Model model) {
//		DeptDO sysDept = sysDeptService.get(deptId);
//		model.addAttribute("sysDept", sysDept);
//		if(Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
//			model.addAttribute("parentDeptName", "无");
//		}else {
//			DeptDO parDept = sysDeptService.get(sysDept.getParentId());
//			model.addAttribute("parentDeptName", parDept.getName());
//		}
//		return  prefix + "/edit";
//	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysDept:add")
	public ResponseResult save(DeptDO sysDept) {
		if (sysDeptService.save(sysDept) > 0) {
			return ResponseResult.ok();
		}
		return ResponseResult.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public ResponseResult update(DeptDO sysDept) {
		if (sysDeptService.update(sysDept) > 0) {
			return ResponseResult.ok();
		}
		return ResponseResult.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:remove")
	public ResponseResult remove(Long deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if(sysDeptService.count(map)>0) {
			return ResponseResult.error(1, "包含下级部门,不允许修改");
		}
		if(sysDeptService.checkDeptHasUser(deptId)) {
			if (sysDeptService.remove(deptId) > 0) {
				return ResponseResult.ok();
			}
		}else {
			return ResponseResult.error(1, "部门包含用户,不允许修改");
		}
		return ResponseResult.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public ResponseResult remove(@RequestParam("ids[]") Long[] deptIds) {
		sysDeptService.batchRemove(deptIds);
		return ResponseResult.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = sysDeptService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/deptTree";
	}

}
