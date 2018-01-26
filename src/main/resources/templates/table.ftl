<div id="contentwrapper">
	<div class="main_content">
		<#include "top.ftl">

		
		<div class="row-fluid">
			<div class="span12">
				<h3 class="heading">快来灌水</h3>

				
					<div class="alert alert-error">
						<a class="close" data-dismiss="alert">×</a>
						<strong>操作信息:

                        欢迎
						<#if username??>
						${username}
						<#else>
                            游客
						</#if>

						



						</strong>
						
					</div>
					
                    <#if username??>
					<div class="btn-group sepH_b">
						<button data-toggle="dropdown" class="btn dropdown-toggle">
							行数 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">


							<li><a href="/user/row/${userid}/5">默认5行</a></li>
							<li><a href="/user/row/${userid}/10">每页10行</a></li>
							<li><a href="/user/row/${userid}/2">每页2行</a></li>
						</ul>
					</div>
                    </#if>
					


				


				<table class="table table-bordered table-striped table_vam"
					id="dt_gal">
					<thead>
						<tr>

							<th class="table_checkbox">序号</th>
							<th style="width: 50px">发布人</th>
							<th style="width: 100px">主帖标题</th>
							<th style="width: 420px">主帖内容</th>
							<th style="width: 60px">发布日期</th>
							<th style="width: 60px">操作</th>
						</tr>
					</thead>
					<tbody>
						<#list pb.data as a>
                    	
						
								<tr>

									<td>${a_index+1}</td>
									<td><a href="/static/upload/.jpg"
										
										title="" class="cbox_single thumbnail">

											<img src="/user/pic/${a.bbsuser.userid}"
											alt="" style="height: 50px; width: 50px" />

									</a>
									
									
									
									</td>
									<td>
									
									
									<a
										href="">${a.title}</a>
									</td>
									<td>${a.content}</td>
									<td>${a.datatime}</td>
									<td>
											
										<!-- 没登陆，游客 uid=0 -->
												
											<!-- 锚点传值 -->
											<#--<a href="" title="灌水" data-toggle="modal"-->
											<#--id="myp" data-backdrop="static"-->
											<#--onclick="rshow(${data.id},${uid},${data.user.id});">-->
												 <#--<i class="icon-eye-open"></i>-->
											<#---->
									<#--</a>-->

											<#if username??><!--已经登陆了-->

                                            <a href="#rshow" title="灌水" data-toggle="modal"
                                               id="myp" data-backdrop="static"
                                               onclick="rshow(${a.id},${userid},${a.bbsuser.userid})">
                                                <i class="icon-eye-open"></i>
                                            </a>
										<#else>
                                            <!--999是游客编号-->
                                            <a href="#rshow" title="灌水" data-toggle="modal"
                                               id="myp" data-backdrop="static"
                                               onclick="rshow(${a.id},999,${a.bbsuser.userid})">
                                                <i class="icon-eye-open"></i>
                                            </a>
										</#if>

											
										
											<!-- 是本人贴可以删除和修改 -->

							                <#if username?? && userid==a.bbsuser.userid>
											<a
												href="/article/delz/${a.id}"
												title="删除本帖"><i class="icon-trash"></i></a>
											</#if>
										
										
										
										
									</td>
										
								</tr>
						</#list>

					</tbody>
				</table>



			</div>
		</div>
		
			<#include "page.ftl">
		

	</div>


</div>