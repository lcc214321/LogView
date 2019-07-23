package cn.cloud.log.basic.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * usercontroller
 * @author Administrator
 *
 */
@Transactional
@RestController
@RequestMapping("${$rmp.ctr.basic}/user")
public class UserController {

}
