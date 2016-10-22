package com.chimade.mes.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chimade.mes.sys.model.PageExtjsGridData;
import com.chimade.mes.sys.model.PageReturnMsgBean;
import com.chimade.mes.sys.model.User;
import com.chimade.mes.sys.service.UserService;
import com.chimade.mes.sys.util.SystemContant;

@Controller
@RequestMapping("/sys")
public class UserController {

	private static final Log log = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserService userService;
	 
	@RequestMapping(value = "/user", method = { RequestMethod.POST})
	public  @ResponseBody   PageReturnMsgBean addUser(@RequestBody User user,HttpServletRequest request){
		boolean b = userService.save(user);
		PageReturnMsgBean mb = new PageReturnMsgBean();
		if ( b == true ){
			mb.setResultFlag(true);
			mb.setSuccess(true);
			mb.setMsg(  SystemContant.CONTROLLER_ADD_SUCCESS );
		} else {
			mb.setMsg(  SystemContant.CONTROLLER_ADD_FAILURE );
		}
		return mb ;
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
	public @ResponseBody   PageReturnMsgBean   deleteUser(@PathVariable Integer id,HttpServletRequest request){
		boolean b = userService.delete(id);
		PageReturnMsgBean mb = new PageReturnMsgBean();
		if ( b == true ){
			mb.setResultFlag(true);
			mb.setSuccess(true);
			mb.setMsg(  SystemContant.CONTROLLER_DELETE_SUCCESS );
		} else {
			mb.setMsg(  SystemContant.CONTROLLER_DELETE_FAILURE );
		}
		return mb ;
	}
 
	@RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
	public @ResponseBody   PageReturnMsgBean   updateUser(@PathVariable Integer id ,@RequestBody User user,HttpServletRequest request){
		boolean b = userService.update(user);
		PageReturnMsgBean mb = new PageReturnMsgBean();
		if ( b == true ){
			mb.setResultFlag(true);
			mb.setSuccess(true);
			mb.setMsg(  SystemContant.CONTROLLER_UPDATE_SUCCESS );
		} else {
			mb.setMsg(  SystemContant.CONTROLLER_UPDATE_FAILURE );
		}
		return mb ;
	}
	
	@RequestMapping(value = "/user/users", method = { RequestMethod.POST })
	public @ResponseBody    PageExtjsGridData<User>   getUserBySearch(@RequestBody User user,HttpServletRequest request){
		List<User> findAll = userService.findBySearch(  user );
		PageExtjsGridData<User> pd = new  PageExtjsGridData<User>( ); 
		pd.setGridDatas(findAll);
		int total = userService.fetchTotalNumberForSearch(user);
		pd.setTotalProperty(  total  );
		return pd ;
	}
	 
}