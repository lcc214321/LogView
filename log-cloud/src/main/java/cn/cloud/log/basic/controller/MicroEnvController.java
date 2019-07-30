package cn.cloud.log.basic.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.cloud.log.basic.service.EnvService;
import cn.cloud.log.basic.service.MicroEnvService;
import cn.cloud.log.basic.bean.MicroEnvInfo;
import cn.cloud.log.basic.po.EnvPo;
import cn.cloud.log.basic.po.MicroServicePo;
import cn.cloud.log.common.constants.ImportContants;
import cn.cloud.log.common.web.ControllerSupport;
import cn.cloud.log.exception.StatusException;
import cn.cloud.log.util.DateUtil;
import cn.cloud.log.util.ExcelUtil;
import io.swagger.annotations.ApiOperation;

@Transactional
@RestController
@RequestMapping("${$rmp.ctr.basic}/microenv")
public class MicroEnvController extends ControllerSupport {
	private static final String[] headers = {"微服务名称", "微服务ip地址", "操作系统(linux,windows)", "微服务登录用户名称", "微服务登录用户密码",
			"采集日志路径", "采集日志名称匹配规则(正则表达式)", "采集日志起始行正则匹配方式", "日志后缀名称(以填入间隔符号作为分隔符)", "间隔符号(默认;)"};
	@Autowired
	MicroEnvService microenvservice;
	@Autowired
	EnvService envservice;

	@ApiOperation(value = "查找所有微服务环境分页", notes = "")
	@GetMapping("microenvPage/{curPage}/{pageSize}")
	@CrossOrigin(allowCredentials = "true")
	public Page<MicroServicePo> getuserPage(@PathVariable Integer curPage, @PathVariable Integer pageSize,
			@RequestParam(name = "queryEnvId", required = false) String envid,
			@RequestParam(name = "queryMicroEnvId", required = false) String micorenvid) {
		Specification<MicroServicePo> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(envid)) {
				predicates.add(cb.equal(root.get("envid"), envid));
			}
			if (!StringUtils.isEmpty(micorenvid)) {
				predicates.add(cb.equal(root.get("id"), micorenvid));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		PageRequest pageRequest = PageRequest.of(curPage, pageSize, new Sort(Direction.DESC, "updatetime"));

		Page<MicroServicePo> page = microenvservice.findAllEnv(specification, pageRequest);
		return page;
	}

	@ApiOperation(value = "新增微服务", notes = "")
	@PostMapping("add")
	@CrossOrigin(allowCredentials = "true")
	public void addEnv(@RequestBody MicroEnvInfo envinfo) {
		MicroServicePo envpo = new MicroServicePo();
		EnvPo poenv = envservice.findEnvByid(envinfo.getEnvid());
		envpo.setEnvname(poenv.getEnvname());
		envpo.setIpaddr(envinfo.getIpaddr());
		envpo.setLoginpassword(envinfo.getLoginpassword());
		envpo.setLoginuser(envinfo.getLoginuser());
		envpo.setLognamepattern(envinfo.getLognamepattern());
		envpo.setLognamepostfix(envinfo.getLognamepostfix());
		envpo.setLogpath(envinfo.getLogpath());
		envpo.setLogpattern(envinfo.getLogpattern());
		envpo.setMicroservicename(envinfo.getMicroservicename());
		envpo.setOstype(envinfo.getOstype());
		envpo.setSplitstr(envinfo.getSplitstr());
		envpo.setUpdatetime(DateUtil.getNowDate());
		microenvservice.SaveEnv(envpo);
	}

	@ApiOperation(value = "修改微服务配置", notes = "")
	@PutMapping("edit")
	@CrossOrigin(allowCredentials = "true")
	public void editUser(@RequestBody MicroEnvInfo envinfo) {
		if (StringUtils.isEmpty(envinfo.getMicroservicename())) {
			throw new StatusException("225", "微服务名称不能为空");
		}
		if (StringUtils.isEmpty(envinfo.getEnvid())) {
			throw new StatusException("225", "环境id不能为空");
		}
		MicroServicePo envpo = microenvservice.findMicroEnvByid(envinfo.getId());
		EnvPo poenv = envservice.findEnvByid(envinfo.getEnvid());
		envpo.setEnvid(poenv.getId());
		envpo.setEnvname(poenv.getEnvname());
		envpo.setIpaddr(envinfo.getIpaddr());
		envpo.setLognamepattern(envinfo.getLognamepattern());
		envpo.setLognamepostfix(envinfo.getLognamepostfix());
		envpo.setLogpath(envinfo.getLogpath());
		envpo.setLogpattern(envinfo.getLogpattern());
		envpo.setMicroservicename(envinfo.getMicroservicename());
		envpo.setOstype(envinfo.getOstype());
		envpo.setSplitstr(envinfo.getSplitstr());
		envpo.setUpdatetime(DateUtil.getNowDate());
		microenvservice.SaveEnv(envpo);
	}

	@ApiOperation(value = "删除微服务", notes = "")
	@DeleteMapping("{envid}")
	@CrossOrigin(allowCredentials = "true")
	public void deleteMicroEnv(@PathVariable long envid) {
		// EnvPo envpo=envservice.findEnvByid(envid);
		// envservice.deleteEnv(envpo);
		MicroServicePo envpo = microenvservice.findMicroEnvByid(envid);
		microenvservice.deleteEnv(envpo);
	}

	@ApiOperation(value = "获取环境下的所有微服务", notes = "")
	@GetMapping("queryMicroByEnvId")
	@CrossOrigin(allowCredentials = "true")
	public List<MicroServicePo> queryMicroEnvByEnvid(
			@RequestParam(name = "queryEnvId", required = false) String envid) {
		List<MicroServicePo> list = microenvservice.findMicroEnvByEnvId(Long.valueOf(envid));
		return list;
		// EnvPo envpo=envservice.findEnvByid(envid);
		// envservice.deleteEnv(envpo);
	}

	@ApiOperation(value = "导入微服务", notes = "")
	@PostMapping("import")
	@CrossOrigin(allowCredentials = "true")
	public void importMicroEnv(@RequestParam MultipartFile file, HttpServletRequest request,@RequestParam Long envid) throws IOException {
		if (file == null) {
			throw new StatusException("227", "上传文件为空");
		}
		String fileName = file.getOriginalFilename();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new StatusException("228", "上传文件格式错误，请上传后缀为.xls或.xlsx的文件");
		}
		
		// 以poi读取文件
		EnvPo envpo=envservice.findEnvByid(envid);
		importFile(envpo,file.getInputStream());
        
	}

	@ApiOperation(value = "下载微服务导入模板", notes = "")
	@GetMapping("importTemplate")
	@CrossOrigin(allowCredentials = "true")
	public void getimportTemplate() throws IOException {
		ClassPathResource resource = new ClassPathResource("templates" + File.separator + "microimporttemplate.xlsx");
		OutputStream out = null;
		InputStream in = null;
		File file=resource.getFile();
		try {
			in = new FileInputStream(file);
			String fileName = URLEncoder.encode("微服务导入模板.xlsx", "UTF-8");
			HttpServletResponse response = getResponse();
			response.reset();
			response.setHeader("Content-Disposition", "inline; filename=" + fileName);
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream;charset=UTF-8");
			out = new BufferedOutputStream(response.getOutputStream());
			IOUtils.copy(in, out);
			out.flush();
		} catch (IOException e) {
			throw new StatusException("229",e.getMessage());
		} finally {
			out.close();
			in.close();
		}
	}
	
	private void importFile(EnvPo envpo,InputStream inputStream){
		try {
			Workbook wb =WorkbookFactory.create(inputStream);
			Sheet sheet=wb.getSheetAt(0);
			Row headerrow=sheet.getRow(0);
			if(!handleheader(headerrow)){
				throw new StatusException("230","表格头错误");
			}
			for(int i=1;i<=sheet.getLastRowNum();i++){
				Row row=sheet.getRow(i);
				MicroServicePo servicepo=new MicroServicePo();
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_microservicename_index)))){
					throw new StatusException("240","第"+"行数据缺少微服务名称");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_ipaddr_index)))){
					throw new StatusException("240","第"+"行数据缺少ip地址");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_ostype_index)))){
					throw new StatusException("240","第"+"行数据缺少操作系统名称");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_loginuser_index)))){
					throw new StatusException("240","第"+"行数据缺少登录用户名");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_loginpassword_index)))){
					throw new StatusException("240","第"+"行数据缺少登录用户密码");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_lognamepattern_index)))){
					throw new StatusException("240","第"+"行数据缺少采集日志名称匹配规则(正则表达式)");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_logpattern_index)))){
					throw new StatusException("240","第"+"行数据缺少采集日志起始行正则匹配方式");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_lognamepostfix_index)))){
					throw new StatusException("240","第"+"行数据缺少日志后缀名称(以填入间隔符号作为分隔符)");
				}
				if(StringUtils.isEmpty(String.valueOf(ExcelUtil.getCellValue(row, ImportContants.template_logpath_index)))){
					throw new StatusException("240","第"+"行数据缺少采集日志路径");
				}
				DataFormatter formatter = new DataFormatter();
				servicepo.setEnvid(envpo.getId());
				servicepo.setEnvname(envpo.getEnvname());
				servicepo.setMicroservicename(formatter.formatCellValue(row.getCell(ImportContants.template_microservicename_index)));
				servicepo.setIpaddr(formatter.formatCellValue(row.getCell(ImportContants.template_ipaddr_index)));
				servicepo.setOstype(formatter.formatCellValue(row.getCell(ImportContants.template_ostype_index)));
				servicepo.setLoginuser(formatter.formatCellValue(row.getCell(ImportContants.template_loginuser_index)));
				servicepo.setLoginpassword(formatter.formatCellValue(row.getCell(ImportContants.template_loginpassword_index)));
				servicepo.setLognamepattern(formatter.formatCellValue(row.getCell(ImportContants.template_lognamepattern_index)));
				servicepo.setLognamepostfix(formatter.formatCellValue(row.getCell(ImportContants.template_logpattern_index)));
				servicepo.setLogpath(formatter.formatCellValue(row.getCell(ImportContants.template_lognamepostfix_index)));
				servicepo.setLogpattern(formatter.formatCellValue(row.getCell(ImportContants.template_logpath_index)));
				servicepo.setSplitstr(formatter.formatCellValue(row.getCell(ImportContants.template_splitstr_index)));
				//判断是否存在,若存在则更新,反之插入
				MicroServicePo orginpo=microenvservice.findMicroEnvByUniqueIndex(servicepo.getIpaddr(), envpo.getId(),servicepo.getMicroservicename());
				if(orginpo!=null){
					servicepo.setId(orginpo.getId());
				}
				servicepo.setUpdatetime(DateUtil.getNowDate());
				microenvservice.SaveEnv(servicepo);

			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	private boolean handleheader(Row headerrow){
		for(int i=0;i<headerrow.getLastCellNum();i++){
			Object cellvalue=ExcelUtil.getCellValue(headerrow, i);
			if(!cellvalue.equals(headers[i])){
				return false;
			}
		}
		return true;
	}
}
