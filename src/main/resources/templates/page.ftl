<div class="pagination">
    <ul>
        <li>
            <#if (pb.currentPage>1)>
            
                <a href="/article/queryz/1">首页</a>
            <#else>
                <span>首页</span>
            </#if>
            <#if (pb.currentPage>1)>

                <a href="/article/queryz/${pb.currentPage-1}">前一页</a>
            <#else>
                <span>前一页</span>
            </#if>
			
        </li>

    	<li>
            <#list 1..pb.maxPage as i>
				<a href="/article/queryz/${i}">/${i}</a>
            </#list>
        </li>

    	<li>

        <#if (pb.currentPage<pb.maxPage)>

            <a href="/article/queryz/${pb.currentPage+1}">下一页</a>
        <#else>
            <span>下一页</span>
        </#if>
			
        </li>

    	<li>

        <#if (pb.currentPage==pb.maxPage)>
            <span>末页</span>

        <#else>
            <a href="/article/queryz/${pb.maxPage}">末页</a>
        </#if>

        </li>

    </ul>
</div>