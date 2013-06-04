package test.status.common;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;


public class AbstractTestOps extends AbstractTestAPI{
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void normal() throws Exception{
		String content = "It is a weibo.";
		assertContent(content);
	}

	@Test
	public void cn() throws Exception{
		String content = "这是一条微博。";
		assertContent(content);
	}
	

	public void assertContent(String content) throws Exception{
		content = content + new Date().getTime();
		
		StatusInfo info = getMicroBlogAPI().add(getAccessToken(), content);
		assertTrue(info != null && info.getId() != null);
		System.out.println(info.toString());
		Thread.sleep(1000);
		
		String id = info.getId();
		
		StatusInfo repost = getMicroBlogAPI().repost(getAccessToken(), id, "--repost--" + new Date().getTime());
		assertTrue(repost != null && repost.getId() != null);
		System.out.println(repost.toString());
		Thread.sleep(1000);

		//MicroBlogCommentInfo reply = getMicroBlogAPI().reply(id, "--reply--" + new Date().getTime());
		//assertTrue(reply != null && reply.getId() != null);
		//System.out.println(reply.toString());
		//Thread.sleep(1000);

		StatusCommentInfo comment = getMicroBlogAPI().comment(getAccessToken(), id, "--comment--" + new Date().getTime());
		assertTrue(comment != null && comment.getId() != null);
		System.out.println(comment.toString());
		Thread.sleep(1000);
		
		getMicroBlogAPI().delete(getAccessToken(), repost.getId());
		Thread.sleep(1000);
		getMicroBlogAPI().delete(getAccessToken(), info.getId());
		Thread.sleep(1000);
		//getMicroBlogAPI().delete(reply.getId());
		//Thread.sleep(1000);
	}
}
