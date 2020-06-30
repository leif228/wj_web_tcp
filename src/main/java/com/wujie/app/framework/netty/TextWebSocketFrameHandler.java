package com.wujie.app.framework.netty;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.wujie.app.business.util.MDA;

/**
 * 处理TextWebSocketFrame
 *
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler<removeChannel> extends SimpleChannelInboundHandler<TextWebSocketFrame> {

//	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//	public static Map<Long, Channel> userChannels = new ConcurrentHashMap<>();
//	/** 所有直播房间 */
//	public static Map<Long, ChannelGroup> rooms = new HashMap<>();
//
//	public final static AttributeKey<UserInfoVo> userInfoVoAttr = AttributeKey.valueOf("USER_INFO_KEY");
//	public final static AttributeKey<Long> roomKey = AttributeKey.valueOf("ROOM_KEY");


//	private RedisTemplate<String, Object> redisTemplate;
//	private LiveInteractiveService liveInteractiveService;
//	private AppLiveUserRoomService appLiveUserRoomService;
//	private AppLiveReportService appLiveReportService;
//	private AppUserService appUserService;

//	@Autowired
//	public TextWebSocketFrameHandler(RedisTemplate<String, Object> redisTemplate,
//									 LiveInteractiveService liveInteractiveService, AppLiveUserRoomService appLiveUserRoomService,
//									 AppLiveReportService appLiveReportService, AppUserService appUserService) {
//		this.redisTemplate = redisTemplate;
//		this.liveInteractiveService = liveInteractiveService;
//		this.appLiveUserRoomService = appLiveUserRoomService;
//		this.appLiveReportService = appLiveReportService;
//		this.appUserService = appUserService;
//	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		log.info("接收消息：{}", msg.text());
		JSONObject param = null;
		try{
			param = JSONObject.parseObject(msg.text());
		}catch (Exception e){
			ctx.channel().close();
			return;
		}
		if (param == null) {
			ctx.channel().close();
			return;
		}
		Integer type = JSONObject.parseObject(param.get("type").toString(), Integer.class);
		Object data = param.get("data");
		switch (type) {
			// 心跳
			case 0:
				receiptMsg(ctx, 0);
				break;
			// 登陆
//			case 1:
//				nettyLogin(ctx, data);
//				break;
//			// 进入房间
//			case 2:
//				nettyAddRoom(ctx, data);
//				break;
//			// 离开房间
//			case 3:
//				nettyGoAwayRoom(ctx, data);
//				break;
//			// 互动聊天
//			case 4:
//				nettyRoomToChatWith(ctx, data);
//				break;
//			// 增加商品点击次数
//			case 5:
//				nettyAddGoodsClick(ctx);
//				break;
//			// 商品上下架，必须是房间创建人才能修改
//			case 11:
//				nettyGoodsUpperAndLowerShelves(ctx, data);
//				break;
//			// 商品推荐，必须是房间创建人才能修改
//			case 12:
//				nettyGoodsRecommendedStatus(ctx, data);
//				break;
//			// 暂停直播
//			case 15:
//				nettyPauseLiveStreaming(ctx);
//				break;
//			// 结束直播
//			case 16:
//				nettyEndLiveBroadcast(ctx);
//				break;
//			// 点赞直播
//			case 21:
//				liveLikeHandle(ctx);
//				break;
//			// 举报直播
//			case 22:
//				liveReportHandle(ctx, data);
//				break;
//			// 被投诉直播踢下线处理
//			case 23:
//				liveKickOffProcessing(ctx);
//				break;
//			// 被投诉直播封号处理
//			case 24:
//				liveTitleProcessing(ctx);
//				break;
			default:
				ctx.channel().close();
				break;
		}
	}
//
//	/**
//	 * @Title nettyGoodsRecommendedStatus
//	 * @Description 修改商品推荐状态
//	 * @Author yangf
//	 * @Date 2020/6/1 16:00
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void nettyGoodsRecommendedStatus(ChannelHandlerContext ctx, Object data) {
//		// 判断数据是否为空
//		if(ObjectUtils.isEmpty(data)){
//			ctx.channel().close();
//			return;
//		}
//		// 判断商品Id以及处理类型是否为空
//		JSONObject jsonObject = JSONObject.parseObject(data.toString());
//		if(ObjectUtils.isEmpty(jsonObject.get("goodsId"))){
//			ctx.channel().close();
//			return;
//		}
//		Long roomId = ctx.channel().attr(roomKey).get();
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		if(!userInfoVo.getUserId().equals(newLiveUserRoom.getAddUserid())){
//			ChannelGroup channels11 = rooms.get(roomId);
//			channels11.remove(ctx.channel());
//			ctx.channel().close();
//			return;
//		}
//		// 设置商品状态
//		Long goodsId = JSONObject.parseObject(jsonObject.get("goodsId").toString(), Long.class);
//		String liveGoodsKey = MessageFormat.format(MDA.CACHE_LIVE_GOODSINFO, roomId);
//		List<AppGoodsInfoVo> appGoodsInfoVos = (List<AppGoodsInfoVo>) redisTemplate.opsForValue().get(liveGoodsKey);
//		for (AppGoodsInfoVo appGoodsInfoVo : appGoodsInfoVos){
//			if(appGoodsInfoVo.getId().equals(goodsId)){
//				appGoodsInfoVo.setRecommendedStatus(Boolean.TRUE);
//			}else{
//				appGoodsInfoVo.setRecommendedStatus(Boolean.FALSE);
//			}
//		}
//		redisTemplate.opsForValue().set(liveGoodsKey, appGoodsInfoVos);
//		ChannelGroup channels = rooms.get(roomId);
//
//		JSONObject dataObj = new JSONObject();
//		dataObj.put("liveUserRoom", newLiveUserRoom);
//		dataObj.put("appGoodsInfoVos", appGoodsInfoVos);
//		JSONObject resutlObj = new JSONObject();
//		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
//		resutlObj.put("msg", "success");
//		resutlObj.put("data", dataObj);
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("type", 12);
//		resultJson.put("data", resutlObj);
//		TextWebSocketFrame resp = new TextWebSocketFrame(resultJson.toString());
//		channels.forEach((channel) ->{
//			if(!channel.equals(ctx.channel())){
//				channel.writeAndFlush(resp);
//			}
//		});
//		receiptMsg(ctx, 12);
//	}
//
//
//
//
//	/**
//	 * @Title liveTitleProcessing
//	 * @Description 被投诉直播封号处理
//	 * @Author yangf
//	 * @Date 2020/6/1 10:22
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void liveTitleProcessing(ChannelHandlerContext ctx) {
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		if(!userInfoVo.getUserType().equals(MDA.numEnum.THREE.ordinal())){
//			ctx.channel().close();
//			return;
//		}
//		Long roomId = ctx.channel().attr(roomKey).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		newLiveUserRoom.setStatus(MDA.numEnum.TWO.ordinal());
//		newLiveUserRoom.setUpdataBy(userInfoVo.getUserId());
//		newLiveUserRoom.setUpdateTime(new Date());
//		appLiveUserRoomService.liveKickOffProcessing(newLiveUserRoom, MDA.numEnum.ONE.ordinal());
//
//		ChannelGroup channels = rooms.get(roomId);
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("type", "24");
//		resultJson.put("data", "永久禁用下线通知");
//		JSONObject usersJson = new JSONObject();
//		usersJson.put("type", "23");
//		usersJson.put("data", "直播被禁用，返回直播大厅");
//		TextWebSocketFrame homeownerResp = new TextWebSocketFrame(resultJson.toJSONString());
//		TextWebSocketFrame usersResp = new TextWebSocketFrame(usersJson.toJSONString());
//		channels.forEach((channel) ->{
//			if(channel.equals(ctx.channel())){
//				channel.writeAndFlush(homeownerResp);
//			}else{
//				channel.writeAndFlush(usersResp);
//			}
//		});
//		receiptMsg(ctx, 24);
//	}
//
//	/**
//	 * @Title liveKickOffProcessing
//	 * @Description 被投诉直播踢下线处理
//	 * @Author yangf
//	 * @Date 2020/6/1 9:23
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void liveKickOffProcessing(ChannelHandlerContext ctx) {
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		if(!userInfoVo.getUserType().equals(MDA.numEnum.THREE.ordinal())){
//			ctx.channel().close();
//			return;
//		}
//		Long roomId = ctx.channel().attr(roomKey).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		newLiveUserRoom.setStatus(MDA.numEnum.TWO.ordinal());
//		newLiveUserRoom.setUpdataBy(userInfoVo.getUserId());
//		newLiveUserRoom.setUpdateTime(new Date());
//		appLiveUserRoomService.liveKickOffProcessing(newLiveUserRoom, MDA.numEnum.ZERO.ordinal());
//
//		ChannelGroup channels = rooms.get(roomId);
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("type", "23");
//		resultJson.put("data", "下线通知");
//		JSONObject usersJson = new JSONObject();
//		usersJson.put("type", "23");
//		usersJson.put("data", "直播被禁用，返回直播大厅");
//		TextWebSocketFrame homeownerResp = new TextWebSocketFrame(resultJson.toJSONString());
//		TextWebSocketFrame usersResp = new TextWebSocketFrame(usersJson.toJSONString());
//		channels.forEach((channel) ->{
//			if(channel.equals(ctx.channel())){
//				channel.writeAndFlush(homeownerResp);
//			}else{
//				channel.writeAndFlush(usersResp);
//			}
//		});
//		receiptMsg(ctx, 23);
//	}
//
//	/**
//	 * @Title liveReportHandle
//	 * @Description 举报直播处理
//	 * @Author yangf
//	 * @Date 2020/5/28 16:29
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void liveReportHandle(ChannelHandlerContext ctx, Object data) {
//		Long roomId = ctx.channel().attr(roomKey).get();
//		if(roomId == null){
//			ctx.channel().close();
//			return;
//		}
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		String reportStatistics = newLiveUserRoom.getReportStatistics();
//		List<JSONObject> reporList = null;
//		// 消息内容
//		JSONObject msgObj = JSONObject.parseObject(data.toString());
//		if(!StringUtils.hasText(reportStatistics)){
//			reporList = new ArrayList<>();
//			addReport(reporList, msgObj);
//			String s = JSONArray.toJSONString(reporList);
//			newLiveUserRoom.setReportStatistics(s);
//			redisTemplate.opsForValue().set(cacheKey, newLiveUserRoom, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//			appLiveReportService.addReport(msgObj, roomId, userInfoVo.getUserId(), s);
//			return;
//		}
//		// 得到投诉内容
//		reporList = JSONArray.parseArray(reportStatistics, JSONObject.class);
//		boolean presence = false;
//		for(JSONObject jsonObject : reporList){
//			if(jsonObject.get("tagId").equals(msgObj.get("tagId"))){
//				int num = Integer.valueOf(jsonObject.get("num").toString());
//				jsonObject.put("num", num + 1);
//				presence = true;
//				break;
//			}
//		}
//		// 投诉内容不存在，添加
//		if(!presence){
//			addReport(reporList, msgObj);
//		}
//		String s = JSONArray.toJSONString(reporList);
//		newLiveUserRoom.setReportStatistics(s);
//		redisTemplate.opsForValue().set(cacheKey, newLiveUserRoom, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//		appLiveReportService.addReport(msgObj, roomId, userInfoVo.getUserId(), s);
//		receiptMsg(ctx, 22);
//	}
//
//	/**
//	 * @Title addReport
//	 * @Description 增加投诉内容
//	 * @Author yangf
//	 * @Date 2020/5/28 17:07
//	 * @param: reporList
//	 * @param: msgObj
//	 * @Return void
//	 */
//	private void addReport(List<JSONObject> reporList, JSONObject msgObj) {
//		JSONObject reportJson = new JSONObject();
//		reportJson.put("tagId", msgObj.get("tagId"));
//		reportJson.put("tagName", msgObj.get("tagName"));
//		reportJson.put("num", 1);
//		reporList.add(reportJson);
//	}
//
//	/**
//	 * @Title liveLikeHandle
//	 * @Description 点赞处理
//	 * @Author yangf
//	 * @Date 2020/5/28 16:22
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void liveLikeHandle(ChannelHandlerContext ctx) {
//		Long roomId = ctx.channel().attr(roomKey).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		newLiveUserRoom.setLikes(newLiveUserRoom.getLikes() + 1);
//		redisTemplate.opsForValue().set(cacheKey, newLiveUserRoom, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//		receiptMsg(ctx, 21);
//	}
//
//	/**
//	 * @Title nettyEndLiveBroadcast
//	 * @Description 结束直播
//	 * @Author yangf
//	 * @Date 2020/5/28 10:48
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void nettyEndLiveBroadcast(ChannelHandlerContext ctx) {
//		Long roomId = ctx.channel().attr(roomKey).get();
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		if(!userInfoVo.getUserId().equals(newLiveUserRoom.getAddUserid())){
//			ChannelGroup channels11 = rooms.get(roomId);
//			channels11.remove(ctx.channel());
//			ctx.channel().close();
//			return;
//		}
//		ChannelGroup channels = rooms.get(roomId);
//
//
//		newLiveUserRoom.setEndTime(new Date());
//		newLiveUserRoom.setStatus(MDA.numEnum.THREE.ordinal());
//		String playbackAddress = LiveUtil.startRecording(roomId);
//		newLiveUserRoom.setPlaybackAddress(playbackAddress);
//
//		JSONObject resutlObj = new JSONObject();
//		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
//		resutlObj.put("msg", "success");
//		resutlObj.put("data", newLiveUserRoom);
//
//		JSONObject resultObj = new JSONObject();
//		resultObj.put("type", 16);
//		resultObj.put("data", resutlObj);
//		TextWebSocketFrame resp = new TextWebSocketFrame(resultObj.toJSONString());
//		channels.forEach((channel) ->{
//			if(!channel.equals(ctx.channel())){
//				channel.writeAndFlush(resp);
//			}
//		});
//		// 修改房间直播状态
//		appLiveUserRoomService.updateLiveUserRomm(newLiveUserRoom);
//		receiptMsg(ctx, 16);
//	}
//
//	/**
//	 * @Title nettyPauseLiveStreaming
//	 * @Description 暂停直播
//	 * @Author yangf
//	 * @Date 2020/5/28 10:36
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void nettyPauseLiveStreaming(ChannelHandlerContext ctx) {
//		Long roomId = ctx.channel().attr(roomKey).get();
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		if(!userInfoVo.getUserId().equals(newLiveUserRoom.getAddUserid())){
//			ChannelGroup channels11 = rooms.get(roomId);
//			channels11.remove(ctx.channel());
//			ctx.channel().close();
//			return;
//		}
//		ChannelGroup channels = rooms.get(roomId);
//		JSONObject resutlObj = new JSONObject();
//		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
//		resutlObj.put("msg", "success");
//		resutlObj.put("data", new JSONObject());
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("type", 15);
//		jsonObject.put("data", resutlObj);
//		TextWebSocketFrame resp = new TextWebSocketFrame(jsonObject.toString());
//		channels.forEach((channel) ->{
//			if(!channel.equals(ctx.channel())){
//				channel.writeAndFlush(resp);
//			}
//		});
//		// 修改房间直播状态
//		appLiveUserRoomService.updateStatusByIdAndDeleteFlag(MDA.numEnum.ONE.ordinal(), roomId, DeleteFlag.RESTORE.ordinal());
//		receiptMsg(ctx, 15);
//	}
//
//	/**
//	 * @Title nettyGoodsUpperAndLowerShelves
//	 * @Description 上下架处理
//	 * @Author yangf
//	 * @Date 2020/5/28 10:30
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void nettyGoodsUpperAndLowerShelves(ChannelHandlerContext ctx, Object data) {
//		// 判断数据是否为空
//		if(ObjectUtils.isEmpty(data)){
//			ctx.channel().close();
//			return;
//		}
//		// 判断商品Id以及处理类型是否为空
//		JSONObject jsonObject = JSONObject.parseObject(data.toString());
//		if(ObjectUtils.isEmpty(jsonObject.get("goodsId")) || ObjectUtils.isEmpty(jsonObject.get("typeId"))){
//			ctx.channel().close();
//			return;
//		}
//		Long roomId = ctx.channel().attr(roomKey).get();
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId);
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		if(!userInfoVo.getUserId().equals(newLiveUserRoom.getAddUserid())){
//			ChannelGroup channels11 = rooms.get(roomId);
//			channels11.remove(ctx.channel());
//			ctx.channel().close();
//			return;
//		}
//
//		// 设置商品状态
//		Long goodsId = JSONObject.parseObject(jsonObject.get("goodsId").toString(), Long.class);
//		Integer typeId = JSONObject.parseObject(jsonObject.get("typeId").toString(), Integer.class);
//		String liveGoodsKey = MessageFormat.format(MDA.CACHE_LIVE_GOODSINFO, roomId);
//		List<AppGoodsInfoVo> appGoodsInfoVos = (List<AppGoodsInfoVo>) redisTemplate.opsForValue().get(liveGoodsKey);
//		if(typeId.equals(MDA.numEnum.ZERO.ordinal())){
//			for (AppGoodsInfoVo appGoodsInfoVo : appGoodsInfoVos){
//				if(appGoodsInfoVo.getId().equals(goodsId)){
//					appGoodsInfoVo.setShelfStatus(Boolean.TRUE);
//				}
//			}
//		}else{
//			for (AppGoodsInfoVo appGoodsInfoVo : appGoodsInfoVos){
//				if(appGoodsInfoVo.getId().equals(goodsId)){
//					appGoodsInfoVo.setShelfStatus(Boolean.FALSE);
//				}
//			}
//		}
//		redisTemplate.opsForValue().set(liveGoodsKey, appGoodsInfoVos);
//		ChannelGroup channels = rooms.get(roomId);
//
//		JSONObject dataObj = new JSONObject();
//		dataObj.put("liveUserRoom", newLiveUserRoom);
//		dataObj.put("appGoodsInfoVos", appGoodsInfoVos);
//		JSONObject resutlObj = new JSONObject();
//		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
//		resutlObj.put("msg", "success");
//		resutlObj.put("data", dataObj);
//		JSONObject resultJson = new JSONObject();
//		resultJson.put("type", 11);
//		resultJson.put("data", resutlObj);
//		TextWebSocketFrame resp = new TextWebSocketFrame(resultJson.toString());
//		channels.forEach((channel) ->{
//			if(!channel.equals(ctx.channel())){
//				channel.writeAndFlush(resp);
//			}
//		});
//		receiptMsg(ctx, 11);
//	}
//
//	/**
//	 * @Title nettyAddGoodsClick
//	 * @Description 商品点击次数处理
//	 * @Author yangf
//	 * @Date 2020/5/28 10:29
//	 * @param: ctx
//	 * @Return void
//	 */
//	private void nettyAddGoodsClick(ChannelHandlerContext ctx) {
//		Long roomId5 = ctx.channel().attr(roomKey).get();
//		String cacheKey5 = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId5);
//		LiveUserRoomTbl newLiveUserRoom5 = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey5);
//		newLiveUserRoom5.setGoodsClick(newLiveUserRoom5.getGoodsClick() + 1);
//		redisTemplate.opsForValue().set(cacheKey5, newLiveUserRoom5, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("code", MDA.numEnum.ZERO.ordinal());
//		jsonObject.put("msg", "success");
//		jsonObject.put("data", new JSONObject());
//		JSONObject res = new JSONObject();
//		res.put("type", 5);
//		res.put("data", jsonObject);
//	}
//
//	/**
//	 * @Title nettyRoomToChatWith
//	 * @Description 聊天处理
//	 * @Author yangf
//	 * @Date 2020/5/28 10:29
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void nettyRoomToChatWith(ChannelHandlerContext ctx, Object data) {
//		Long roomId4 = ctx.channel().attr(roomKey).get();
//		String cacheKey4 = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId4);
//		LiveUserRoomTbl newLiveUserRoom4 = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey4);
//
//		ChannelGroup channels4 = rooms.get(roomId4);
//		UserInfoVo userInfoVo1 = ctx.channel().attr(userInfoVoAttr).get();
//		// 保存聊天记录, 增加互动次数
//
//		String content = data.toString();
//		liveInteractiveService.save(content, roomId4, userInfoVo1.getUserId());
//		newLiveUserRoom4.setInteractions(newLiveUserRoom4.getInteractions() + 1);
//		redisTemplate.opsForValue().set(cacheKey4, newLiveUserRoom4, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//		// 给所有房间人员推送消息
//		JSONObject resultData = new JSONObject();
//		resultData.put("userInfo", userInfoVo1);
//		resultData.put("roomInfo", newLiveUserRoom4);
//
//		JSONObject jsonObject4 = JSONObject.parseObject(data.toString());
//		jsonObject4.put("code", MDA.numEnum.ZERO.ordinal());
//		jsonObject4.put("msg", "success");
//		jsonObject4.put("data", resultData);
//
//
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("type", 4);
//		jsonObject.put("data", jsonObject);
//
//		TextWebSocketFrame resp4 = new TextWebSocketFrame(jsonObject4.toJSONString());
//		channels4.forEach((channel) ->{
//			if(!channel.equals(channels4)){
//				channel.writeAndFlush(resp4);
//			}
//		});
//		receiptMsg(ctx, 4);
//	}
//
//	/**
//	 * @Title nettyGoAwayRoom
//	 * @Description 离开房间处理
//	 * @Author yangf
//	 * @Date 2020/5/28 10:28
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void nettyGoAwayRoom(ChannelHandlerContext ctx, Object data) {
//		Long roomId3 = ctx.channel().attr(roomKey).get();
//		String cacheKey3 = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomId3);
//		LiveUserRoomTbl newLiveUserRoom3 = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey3);
//
//		ChannelGroup channels3 = rooms.get(roomId3);
//		channels3.remove(ctx.channel());
//		newLiveUserRoom3.setOnlineUsers(channels3.size());
//		redisTemplate.opsForValue().set(cacheKey3, newLiveUserRoom3, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//
//		JSONObject jsonObject3 = JSONObject.parseObject(data.toString());
//		jsonObject3.put("data", newLiveUserRoom3);
//		jsonObject3.put("code", 0);
//		jsonObject3.put("msg", "success");
//
//		JSONObject res = new JSONObject();
//		res.put("type", 3);
//		res.put("data", jsonObject3);
//		TextWebSocketFrame resp3 = new TextWebSocketFrame(res.toJSONString());
//		channels3.forEach((channel) ->{
//			if(!channel.equals(channels3)){
//				channel.writeAndFlush(resp3);
//			}
//		});
//		receiptMsg(ctx, 3);
//	}
//
//	/**
//	 * @Title nettyAddRoom
//	 * @Description 加入房间处理
//	 * @Author yangf
//	 * @Date 2020/6/1 9:43
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void nettyAddRoom(ChannelHandlerContext ctx, Object data) {
//		Long roomId = (Long) data;
//		UserInfoVo userInfoVo = ctx.channel().attr(userInfoVoAttr).get();
//		LiveUserRoomTbl roomInfo = appLiveUserRoomService.findLiveRoomInfoByRoomIdAndUserIdAndDeleteFlag(roomId, userInfoVo.getUserId());
//		if(ObjectUtils.isEmpty(roomInfo)){
//			ctx.channel().close();
//			return;
//		}
//		ctx.channel().attr(roomKey).set(roomInfo.getId());
//		String cacheKey = MessageFormat.format(MDA.CACHE_ROOM_INFO, roomInfo.getId());
//		if(!rooms.containsKey(roomInfo.getId())){
//			// 需要直播房间信息, 并初始化观看人数以及点赞数量到redis中
//			rooms.put(roomInfo.getId(), new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
//			if(!redisTemplate.hasKey(cacheKey)){
//				redisTemplate.opsForValue().set(cacheKey, roomInfo, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//			}
//			ChannelGroup channels = rooms.get(roomInfo.getId());
//			channels.add(ctx.channel());
//			return;
//		}
//		// 给其他人推送消息
//		ChannelGroup channels1 = rooms.get(roomInfo.getId());
//		LiveUserRoomTbl newLiveUserRoom = (LiveUserRoomTbl) redisTemplate.opsForValue().get(cacheKey);
//		newLiveUserRoom.setViewers(newLiveUserRoom.getViewers() + 1);
//		newLiveUserRoom.setOnlineUsers(channels1.size());
//		if(channels1.size() > newLiveUserRoom.getMaxOnlineUsers()){
//			newLiveUserRoom.setMaxOnlineUsers(channels1.size());
//		}
//		redisTemplate.opsForValue().set(cacheKey, newLiveUserRoom, MDA.numEnum.THREE.ordinal(), TimeUnit.DAYS);
//
//		JSONObject resutlObj = new JSONObject();
//		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
//		resutlObj.put("msg", "success");
//		resutlObj.put("data", ctx.channel().attr(userInfoVoAttr).get());
//
//		JSONObject res = new JSONObject();
//		res.put("type", 2);
//		res.put("data", resutlObj);
//		TextWebSocketFrame resp = new TextWebSocketFrame(res.toJSONString());
//		ChannelGroup channels = rooms.get(roomInfo.getId());
//		channels.add(ctx.channel());
//		channels1.forEach((channel) ->{
//			channel.writeAndFlush(resp);
//		});
//		// receiptMsg(ctx, 2);
//	}
//
//	/**
//	 * @Title nettyLogin
//	 * @Description netty登陆处理
//	 * @Author yangf
//	 * @Date 2020/5/28 10:26
//	 * @param: ctx
//	 * @param: data
//	 * @Return void
//	 */
//	private void nettyLogin(ChannelHandlerContext ctx, Object data) {
//		if(ObjectUtils.isEmpty(data)){
//			ctx.channel().close();
//			return;
//		}
//		JSONObject jsonObject = JSONObject.parseObject(data.toString());
//		Object userId1 = jsonObject.get("userId");
//		Long userId = JSON.parseObject(userId1.toString(), Long.class);
//		UserInfoVo userInfoVo = appUserService.getUserInfoById(userId);
//		if (userChannels.containsKey(userInfoVo.getUserId())){
//			Channel channel = userChannels.get(userInfoVo.getUserId());
//			// TODO 需要定义返回的JSON格式，通知用户被挤下去了
//			channel.close();
//		}
//		ctx.channel().attr(userInfoVoAttr).set(userInfoVo);
//		userChannels.put(userInfoVo.getUserId(), ctx.channel());
//		receiptMsg(ctx, 1);
//	}
//
	private void receiptMsg(ChannelHandlerContext ctx, int type) {
		JSONObject resultJson = new JSONObject();
		JSONObject resutlObj = new JSONObject();
		resutlObj.put("code", MDA.numEnum.ZERO.ordinal());
		resutlObj.put("msg", "success");

		resultJson.put("type", type);
		resultJson.put("data", resutlObj);
		TextWebSocketFrame homeownerResp = new TextWebSocketFrame(resultJson.toJSONString());
		ctx.channel().writeAndFlush(homeownerResp);
	}
//
//	@Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming = ctx.channel();
//		log.info("新连接：{}", incoming);
//        // Broadcast a message to multiple Channels
//        channels.add(incoming);
//		// userChannels.put()
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming = ctx.channel();
//        if(incoming.hasAttr(userInfoVoAttr)){
//			userChannels.remove(incoming.attr(userInfoVoAttr).get().getUserId());
//		}
//		// 删除房间通道，
//		if(incoming.hasAttr(roomKey)){
//			Long roomId = incoming.attr(roomKey).get();
//			rooms.get(roomId).remove(incoming);
//		}
//	}
//
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//	}
//
//	@Override
//	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming = ctx.channel();
//		if(incoming.hasAttr(userInfoVoAttr)){
//			userChannels.remove(incoming.attr(userInfoVoAttr).get().getUserId());
//		}
//		// 删除房间通道，
//		if(incoming.hasAttr(roomKey)){
//			Long roomId = incoming.attr(roomKey).get();
//			rooms.get(roomId).remove(incoming);
//		}
//	}
//
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
//			throws Exception {
//		cause.printStackTrace();
//        ctx.close();
//	}
}
