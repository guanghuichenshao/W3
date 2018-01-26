
<form name="addarticle" id="addarticle"
      action="/article/saveZ"
      method="post">
    <table style="width: 500px; height: auto;">
        <tr>
            <td>标题：</td>
            <td><input type="text" value=""
                       name="title" id="title"
                       style="width: 100%; height: 28px; padding-left: 5px; margin-top: 3px;" />
                <!-- 有 id就是回帖 -->


                <input type="hidden" id="postrootaction" name="action" value="add" />
                <input type="hidden" id="postrootid" name="rootid" value="" />

                 <#if username??>
                 <input type="hidden" name="bbsuser.userid" id="userid" value="${userid}" />
                 <#else>
                     <input type="hidden" name="userid" id="userid" value="" />
                 </#if>
            </td>
        </tr>
        <tr>
            <td valign="top">内容：</td>
            <td><textarea id="content" name="content" id="content" rows="10"
                          cols="30"></textarea>


            </td>
        </tr>


    </table>


</form>