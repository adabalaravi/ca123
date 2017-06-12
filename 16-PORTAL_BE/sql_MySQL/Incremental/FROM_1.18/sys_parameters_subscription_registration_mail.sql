ALTER TABLE sys_parameters MODIFY param_value VARCHAR(5000);

INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'SUBSCRIPTION_MAIL_SUBJECT', 'Cricket Australia Live: The Official App – Subscription Confirmation', 'SUBSCRIPTION_MAIL_SUBJECT', 'TBD', 1, 'Subject for subscription mail', 'STRING');

INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'SUBSCRIPTION_MAIL_TEXT', '<div style="font-family:Times New Roman">
Dear <#>Subscriber<#>,  
<br/><br/>
This email confirms your purchase of the following subscription:<br/><br/>
<table>
<tr><td align=right>Name of Subscription:</td><td><b>Cricket Australia Live Streaming: #1# </b></td></tr>
<tr><td align=right>Name of Application:</td><td><b>You can view live streaming on <i>Cricket Australia Live: The Official App </i></b></td></tr>
<tr><td align=right>Content Provider:</td><td><b>			Cricket Australia </b></td></tr>
<tr><td align=right>Start date:</td><td>	<b>		#2# </b></td></tr>
<tr><td align=right>End date:</td><td> <b>#3#</b></td></tr>
<tr><td align=right>Price:</td><td>		<b>				$#4#</b></td></tr>
<tr><td align=right>Payment Method:</td><td><b>		Credit Card</b></td></tr>
<tr><td align=right>Receipt Number:</td><td><b>		#5#</b></td></tr>
</table>

<br/>
You can view live streaming on <i>Cricket Australia Live: The Official App </i>(for Apple and Android) or at <a href="http://www.cricket.com.au/live"><span style=color:windowtext>cricket.com.au/live</span></a>. Remember that live streaming is only available to watch within Australia. Data charges may apply from your mobile carrier or internet service provider depending on your plan. You should speak directly to your provider if you have any queries in relation to this. 
<br/><br/>

Thanks,<br/><br/>

Cricket Australia<br/>
@cricketaus<br/><br/>
<span style=font-size:85%;line-height:120%>
<i>Cricket Australia Live: The Official App</i> full terms and conditions are located at: <a
  href="http://www.cricket.com.au/official%20app/connect-terms-conditions"><span style=color:windowtext>http://www.cricket.com.au/official%20app/connect-terms-conditions</span></a>
</span>
</div>', 'SUBSCRIPTION_MAIL_TEXT', 'TBD', 1, 'Text for subscription mail', 'STRING');


INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'REGISTRATION_MAIL_SUBJECT', 'Cricket Australia Live: The Official App – Registration Confirmation', 'REGISTRATION_MAIL_SUBJECT', 'TBD', 1, 'Subject for registration mail', 'STRING');


INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'REGISTRATION_MAIL_TEXT', '<div style="font-family:Times New Roman">Dear Cricket Australia Live App User,
<br/><br/>
Thanks for connecting with us via the App. As a connected user you can now enjoy up-to-the-minute news, all the latest scores and stats, and exclusive video highlights and interviews. The app is the perfect way to stay up-to-date with the Australian teams during the current and upcoming overseas series''.
<br/><br/>
While Live Streaming has concluded for the current season, stay tuned later in the year as we kick-off our live streaming service for the 2014/15 home season*.
 
<br/><br/>
As a bonus for registering your details with us, you receive an automatic membership to the official fan club of Cricket Australia - The Australian Cricket Family (ACF).
<br/><br/>
 
As a member, you''ll receive:
<ul type="disc">
<li>Member only cricket news and exclusives;</li>
<li>Priority ticket access** before the general public for international cricket tickets each season;</li>
<li>Exclusive merchandise, travel and partner offers.</li>
</ul>
 
You''ll receive an ACF welcome email within seven days - providing your unique member number. For more information about the ACF or to manage your communication preferences once you have received your initial welcome email you can visit <a
href="http://www.cricket.com.au/acf/Login?item=%2ffans%2faustralian-cricket-family&user=extranet%5cAnonymous&site=website <http://www.cricket.com.au/acf/Login?item=/fans/australian-cricket-family&user=extranet%5cAnonymous&site=website> ">http://www.cricket.com.au/acf/Login?item=%2ffans%2faustralian-cricket-family&user=extranet%5cAnonymous&site=website <http://www.cricket.com.au/acf/Login?item=/fans/australian-cricket-family&user=extranet%5cAnonymous&site=website> 
</a>.
 
<br/><br/>
Visit the <i>Cricket Australia Live: The Official App</i> Support page if you have any questions about using the app, or need help with an issue <a href="https://cricketaustralia.zendesk.com/"><span style=color:blue>https://cricketaustralia.zendesk.com/</span></a> <br/><br/>Thanks for your support of Australian Cricket,<br/><br/>
 
Cricket Australia<br/>
@cricketaus<br/><br/>
<span style="font-size:85%;line-height:120%;color:windowtext">
 
<i>Cricket Australia Live: The Official App</i> full terms and conditions are located at: <a
href="http://www.cricket.com.au/official%20app/connect-terms-conditions"><span style=color:blue>http://www.cricket.com.au/official%20app/connect-terms-conditions</span></a>
<br/><br/>
*Excludes ICC events such as the ICC Cricket World Cup 2015.<br/>
**Conditions apply to all priority access offers. For full terms and conditions of the Australian Cricket Family
<a href="http://links.cricketaustralia.mkt4158.com/ctt?kn=9&ms=NzQ4MTcyMgS2&r=NzIyOTcxMzQ5MTkS1&b=0&j=MTAwOTA3MjQ0S0&mt=1&rt=0"
target="_blank"><span style=color:blue>click here.</span></a><br/>
</span>
</div>', 'REGISTRATION_MAIL_TEXT', 'TBD', 1, 'Text for registration mail', 'STRING');


INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'SUBSCRIPTION_MAIL_TEXT_MEMBERSHIP', '<div style="font-family:Times New Roman">
Dear <#>Subscriber<#>,  
<br/><br/>
This email confirms your purchase of the following subscription:<br/><br/>
<table>
<tr><td align=right>Name of Subscription:</td><td><b>Cricket Australia Live Streaming: ACF #1# </b></td></tr>
<tr><td align=right>Name of Application:</td><td><b>You can view live streaming on <i>Cricket Australia Live: The Official App </i></b></td></tr>
<tr><td align=right>Content Provider:</td><td><b>			Cricket Australia </b></td></tr>
<tr><td align=right>Start date:</td><td>	<b>		#2# </b></td></tr>
<tr><td align=right>End date:</td><td> <b>#3#</b></td></tr>
<tr><td align=right>Price:</td><td>		<b>				$#4#</b></td></tr>
<tr><td align=right>Payment Method:</td><td><b>		Credit Card</b></td></tr>
<tr><td align=right>Receipt Number:</td><td><b>		#5#</b></td></tr>
</table>

<br/>
You can view live streaming on <i>Cricket Australia Live: The Official App </i>(for Apple and Android) or at <a href="http://www.cricket.com.au/live"><span style=color:windowtext>cricket.com.au/live</span></a>. Remember that live streaming is only available to watch within Australia. Data charges may apply from your mobile carrier or internet service provider depending on your plan. You should speak directly to your provider if you have any queries in relation to this. 
<br/><br/>

Thanks,<br/><br/>

Cricket Australia<br/>
@cricketaus<br/><br/>
<span style=font-size:85%;line-height:120%>
<i>Cricket Australia Live: The Official App</i> full terms and conditions are located at: <a
  href="http://www.cricket.com.au/official%20app/connect-terms-conditions"><span style=color:windowtext>http://www.cricket.com.au/official%20app/connect-terms-conditions</span></a>
</span>
</div>', 'SUBSCRIPTION_MAIL_TEXT_MEMBERSHIP', 'TBD', 1, 'Text for subscription mail membership', 'STRING');

INSERT INTO `sys_parameters` (`param_group`, `param_name`, `param_value`, `param_label`, `param_platform`, `param_order`, `param_description`, `param_type`) VALUES ('costpa', 'MAIL_FROM_ADDRESS_NO_REPLY', 'do_not_reply_AppSupport@cricket.com.au', 'MAIL_FROM_ADDRESS_NO_REPLY', 'TBD', 1, 'From Address for no-reply', 'STRING');