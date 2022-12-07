<?xml version="1.0" encoding="utf-8"?>
<data-bus>
	<QINGQIUXX>
		<DIANZISQAJID>${pid}</DIANZISQAJID>
		<SHENQINGFS>0</SHENQINGFS>
		<YEWULX>${shenqinglx}</YEWULX>
		<WENJIANLX>0</WENJIANLX>
		<TIJIAORDM></TIJIAORDM>
		<FAMINGMC>${famingmc}</FAMINGMC>
		<SHENQINGH></SHENQINGH>
		<WEINEIBH></WEINEIBH>
		<GUOJISQH></GUOJISQH>
		<NEIBUBH>${nbbh}</NEIBUBH>
		<BAOID></BAOID>
	</QINGQIUXX>
	<WENJIANBYSXX>
		<BAOTOUXX>
			<WENJIANBBZ></WENJIANBBZ>
			<WENJIANGS>${files?size}</WENJIANGS>
		</BAOTOUXX>
		<#list files as file>
			<#if file.extName!=".PDF">
				<#assign extName=".XML">
			<#else>
				<#assign  extName=".PDF">
			</#if>
			<BAONEIWJXX>
				<WENJIANBZ>${file.code}${extName}</WENJIANBZ>
				<GESHILX>${extName}</GESHILX>
				<BIAOGEDM>${file.code}</BIAOGEDM>
				<XIANGDUILJ>${file.code}\${file.code}${extName}</XIANGDUILJ>
				<DTDVersion>1.0</DTDVersion>
				<PAGES>${file.pages}</PAGES>
				<COUNTS>0</COUNTS>
				<SHIFOUBZ>0</SHIFOUBZ>
				<WENJIANMC>${file.type}</WENJIANMC>
				<ZHENGMINGYT></ZHENGMINGYT>
			</BAONEIWJXX>
		</#list>
		<#list addFiles as file>
			<#if file.extName!=".PDF">
				<#assign extName=".XML">
			<#else>
				<#assign  extName=".PDF">
			</#if>
			<BAONEIWJXX>
				<WENJIANBZ>${file.code}${extName}</WENJIANBZ>
				<GESHILX>${extName}</GESHILX>
				<BIAOGEDM>${file.code}</BIAOGEDM>
				<XIANGDUILJ>${file.code}\${file.code}${extName}</XIANGDUILJ>
				<DTDVersion>1.0</DTDVersion>
				<PAGES>${file.pages}</PAGES>
				<COUNTS>0</COUNTS>
				<SHIFOUBZ>1</SHIFOUBZ>
				<WENJIANMC>${file.type}</WENJIANMC>
				<ZHENGMINGYT></ZHENGMINGYT>
			</BAONEIWJXX>
		</#list>
	</WENJIANBYSXX>
</data-bus>