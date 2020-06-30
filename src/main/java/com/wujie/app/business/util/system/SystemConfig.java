package com.wujie.app.business.util.system;


import com.wujie.app.framework.util.base.impl.BaseServiceImpl;

/**
 * 
 *************************************************
 * SystemConfig实体
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年2月13日 创建文件                                            
 * @See                            
 *************************************************
 */
public class SystemConfig extends BaseServiceImpl {
	/*系统*/
	private static String sysCompanyName;
	private static String sysSoftwareName;
	private static String appSoftwareName;
	private static String copyright;
	private static String domain;
	private static String devDomain;
	private static String hostDomain;
	/*微信*/
	private static String wxAppId;
	private static String wxAppSecret;
	private static String wxMchId;
	private static String wxPartnerKey;
	private static String wxPayCertPath;
	/*支付宝*/
	private static String aliAppId;
	private static String aliMerchantPrivateKey;
	private static String aliMerchantPublicKey;
	private static String aliPayPublicKey;

	private static String qiNiuYunAccessKey;
	private static String qiNiuYunSecretKey;
	private static String qiNiuYunPushFlowDomain;
	private static String qiNiuYunLiveAddressLine1;
	private static String qiNiuYunLiveAddressLine2;
	private static String qiNiuYunLiveAddressLine3;

	/** 提成总比例 */
	private static String commissionPercentage;
	/** 注册提成比例 */
	private static String registerCommissionPercentage;
	/** 服务提成比例 */
	private static String serviceCommissionRatio;
	/*申请管家*/
	private static String applyBigButlerMoney;
	private static String applySmallButlerMoney;

	private static String accessKeyId;
	private static String accessKeySecret;
	private static String imgBucketName;
	private static String endpoint;
	private static String imgDomain;
	private static String shareLogo;

	private static String signMoney1;
	private static String signMoney2;
	private static String signMoney3;
	private static String signMoney4;
	private static String signMoney5;
	private static String signMoney6;
	private static String signMoney7;

	private static String expressKey;
	private static String expressCustomer;

	private static String personalTaxSwitch;
	private static String maximumAmountWithdrawn;
	
	private static String shareGoodsId;

	public static String getSysCompanyName() {
		return sysCompanyName;
	}

	public static void setSysCompanyName(String sysCompanyName) {
		SystemConfig.sysCompanyName = sysCompanyName;
	}

	public static String getSysSoftwareName() {
		return sysSoftwareName;
	}

	public static void setSysSoftwareName(String sysSoftwareName) {
		SystemConfig.sysSoftwareName = sysSoftwareName;
	}

	public static String getAppSoftwareName() {
		return appSoftwareName;
	}

	public static void setAppSoftwareName(String appSoftwareName) {
		SystemConfig.appSoftwareName = appSoftwareName;
	}

	public static String getCopyright() {
		return copyright;
	}

	public static void setCopyright(String copyright) {
		SystemConfig.copyright = copyright;
	}

	public static String getDomain() {
		return domain;
	}

	public static void setDomain(String domain) {
		SystemConfig.domain = domain;
	}

	public static String getDevDomain() {
		return devDomain;
	}

	public static void setDevDomain(String devDomain) {
		SystemConfig.devDomain = devDomain;
	}

	public static String getHostDomain() {
		return hostDomain;
	}

	public static void setHostDomain(String hostDomain) {
		SystemConfig.hostDomain = hostDomain;
	}

	public static String getWxAppId() {
		return wxAppId;
	}

	public static void setWxAppId(String wxAppId) {
		SystemConfig.wxAppId = wxAppId;
	}

	public static String getWxAppSecret() {
		return wxAppSecret;
	}

	public static void setWxAppSecret(String wxAppSecret) {
		SystemConfig.wxAppSecret = wxAppSecret;
	}

	public static String getWxMchId() {
		return wxMchId;
	}

	public static void setWxMchId(String wxMchId) {
		SystemConfig.wxMchId = wxMchId;
	}

	public static String getWxPartnerKey() {
		return wxPartnerKey;
	}

	public static void setWxPartnerKey(String wxPartnerKey) {
		SystemConfig.wxPartnerKey = wxPartnerKey;
	}

	public static String getWxPayCertPath() {
		return wxPayCertPath;
	}

	public static void setWxPayCertPath(String wxPayCertPath) {
		SystemConfig.wxPayCertPath = wxPayCertPath;
	}

	public static String getAliAppId() {
		return aliAppId;
	}

	public static void setAliAppId(String aliAppId) {
		SystemConfig.aliAppId = aliAppId;
	}

	public static String getAliMerchantPrivateKey() {
		return aliMerchantPrivateKey;
	}

	public static void setAliMerchantPrivateKey(String aliMerchantPrivateKey) {
		SystemConfig.aliMerchantPrivateKey = aliMerchantPrivateKey;
	}

	public static String getAliMerchantPublicKey() {
		return aliMerchantPublicKey;
	}

	public static void setAliMerchantPublicKey(String aliMerchantPublicKey) {
		SystemConfig.aliMerchantPublicKey = aliMerchantPublicKey;
	}

	public static String getAliPayPublicKey() {
		return aliPayPublicKey;
	}

	public static void setAliPayPublicKey(String aliPayPublicKey) {
		SystemConfig.aliPayPublicKey = aliPayPublicKey;
	}

	public static String getQiNiuYunAccessKey() {
		return qiNiuYunAccessKey;
	}

	public static void setQiNiuYunAccessKey(String qiNiuYunAccessKey) {
		SystemConfig.qiNiuYunAccessKey = qiNiuYunAccessKey;
	}

	public static String getQiNiuYunSecretKey() {
		return qiNiuYunSecretKey;
	}

	public static void setQiNiuYunSecretKey(String qiNiuYunSecretKey) {
		SystemConfig.qiNiuYunSecretKey = qiNiuYunSecretKey;
	}

	public static String getQiNiuYunPushFlowDomain() {
		return qiNiuYunPushFlowDomain;
	}

	public static void setQiNiuYunPushFlowDomain(String qiNiuYunPushFlowDomain) {
		SystemConfig.qiNiuYunPushFlowDomain = qiNiuYunPushFlowDomain;
	}

	public static String getQiNiuYunLiveAddressLine1() {
		return qiNiuYunLiveAddressLine1;
	}

	public static void setQiNiuYunLiveAddressLine1(String qiNiuYunLiveAddressLine1) {
		SystemConfig.qiNiuYunLiveAddressLine1 = qiNiuYunLiveAddressLine1;
	}

	public static String getQiNiuYunLiveAddressLine2() {
		return qiNiuYunLiveAddressLine2;
	}

	public static void setQiNiuYunLiveAddressLine2(String qiNiuYunLiveAddressLine2) {
		SystemConfig.qiNiuYunLiveAddressLine2 = qiNiuYunLiveAddressLine2;
	}

	public static String getQiNiuYunLiveAddressLine3() {
		return qiNiuYunLiveAddressLine3;
	}

	public static void setQiNiuYunLiveAddressLine3(String qiNiuYunLiveAddressLine3) {
		SystemConfig.qiNiuYunLiveAddressLine3 = qiNiuYunLiveAddressLine3;
	}

	public static String getCommissionPercentage() {
		return commissionPercentage;
	}

	public static void setCommissionPercentage(String commissionPercentage) {
		SystemConfig.commissionPercentage = commissionPercentage;
	}

	public static String getRegisterCommissionPercentage() {
		return registerCommissionPercentage;
	}

	public static void setRegisterCommissionPercentage(String registerCommissionPercentage) {
		SystemConfig.registerCommissionPercentage = registerCommissionPercentage;
	}

	public static String getServiceCommissionRatio() {
		return serviceCommissionRatio;
	}

	public static void setServiceCommissionRatio(String serviceCommissionRatio) {
		SystemConfig.serviceCommissionRatio = serviceCommissionRatio;
	}

	public static String getAccessKeyId() {
		return accessKeyId;
	}

	public static void setAccessKeyId(String accessKeyId) {
		SystemConfig.accessKeyId = accessKeyId;
	}

	public static String getAccessKeySecret() {
		return accessKeySecret;
	}

	public static void setAccessKeySecret(String accessKeySecret) {
		SystemConfig.accessKeySecret = accessKeySecret;
	}

	public static String getImgBucketName() {
		return imgBucketName;
	}

	public static void setImgBucketName(String imgBucketName) {
		SystemConfig.imgBucketName = imgBucketName;
	}

	public static String getEndpoint() {
		return endpoint;
	}

	public static void setEndpoint(String endpoint) {
		SystemConfig.endpoint = endpoint;
	}

	public static String getImgDomain() {
		return imgDomain;
	}

	public static void setImgDomain(String imgDomain) {
		SystemConfig.imgDomain = imgDomain;
	}

	public static String getShareLogo() {
		return shareLogo;
	}

	public static void setShareLogo(String shareLogo) {
		SystemConfig.shareLogo = shareLogo;
	}

	public static String getSignMoney1() {
		return signMoney1;
	}

	public static void setSignMoney1(String signMoney1) {
		SystemConfig.signMoney1 = signMoney1;
	}

	public static String getSignMoney2() {
		return signMoney2;
	}

	public static void setSignMoney2(String signMoney2) {
		SystemConfig.signMoney2 = signMoney2;
	}

	public static String getSignMoney3() {
		return signMoney3;
	}

	public static void setSignMoney3(String signMoney3) {
		SystemConfig.signMoney3 = signMoney3;
	}

	public static String getSignMoney4() {
		return signMoney4;
	}

	public static void setSignMoney4(String signMoney4) {
		SystemConfig.signMoney4 = signMoney4;
	}

	public static String getSignMoney5() {
		return signMoney5;
	}

	public static void setSignMoney5(String signMoney5) {
		SystemConfig.signMoney5 = signMoney5;
	}

	public static String getSignMoney6() {
		return signMoney6;
	}

	public static void setSignMoney6(String signMoney6) {
		SystemConfig.signMoney6 = signMoney6;
	}

	public static String getSignMoney7() {
		return signMoney7;
	}

	public static void setSignMoney7(String signMoney7) {
		SystemConfig.signMoney7 = signMoney7;
	}

	public static String getExpressKey() {
		return expressKey;
	}

	public static void setExpressKey(String expressKey) {
		SystemConfig.expressKey = expressKey;
	}

	public static String getExpressCustomer() {
		return expressCustomer;
	}

	public static void setExpressCustomer(String expressCustomer) {
		SystemConfig.expressCustomer = expressCustomer;
	}

	public static String getPersonalTaxSwitch() {
		return personalTaxSwitch;
	}

	public static void setPersonalTaxSwitch(String personalTaxSwitch) {
		SystemConfig.personalTaxSwitch = personalTaxSwitch;
	}

	public static String getMaximumAmountWithdrawn() {
		return maximumAmountWithdrawn;
	}

	public static void setMaximumAmountWithdrawn(String maximumAmountWithdrawn) {
		SystemConfig.maximumAmountWithdrawn = maximumAmountWithdrawn;
	}

	public static String getShareGoodsId() {
		return shareGoodsId;
	}

	public static void setShareGoodsId(String shareGoodsId) {
		SystemConfig.shareGoodsId = shareGoodsId;
	}

	public static String getApplyBigButlerMoney() {
		return applyBigButlerMoney;
	}

	public static void setApplyBigButlerMoney(String applyBigButlerMoney) {
		SystemConfig.applyBigButlerMoney = applyBigButlerMoney;
	}

	public static String getApplySmallButlerMoney() {
		return applySmallButlerMoney;
	}

	public static void setApplySmallButlerMoney(String applySmallButlerMoney) {
		SystemConfig.applySmallButlerMoney = applySmallButlerMoney;
	}
}
