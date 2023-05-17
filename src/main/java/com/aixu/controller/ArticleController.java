package com.aixu.controller;

import com.aixu.entity.RestBean;
import com.aixu.entity.dto.ArticleDetailsDTO;
import com.aixu.entity.dto.UserStarArticleDTO;
import com.aixu.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
//    @Resource
//    private ArticleService articleService;

    @GetMapping("/getAllArticle")
    public RestBean<List<ArticleDetailsDTO>> getAllArticle(@RequestParam("page") Integer page){
        if (page==null) return RestBean.failure(401);
        return RestBean.success(articleService.getAllArticle(page, 10).getRows());
    }
    @PostMapping("/createArticle")
    public RestBean<String> createArticle(@RequestParam("title") String title,
                                          @RequestParam("content") String content,
                                          @RequestParam("userId") Integer userId){

        String s = articleService.createArticle(title,content,userId);
        if( s == null){
            return RestBean.success("文章发布成功");
        }

        return RestBean.failure(401,s);
    }

    /**
     * 获取文章详情页
     * @param articleId 文章ID
     * @param accountId 用户ID
     * @return 文章对象
     */
    @GetMapping("/getArticle")
    public RestBean<ArticleDetailsDTO> getArticle(@RequestParam("articleId") Integer articleId,
                                       @RequestParam("accountId") Integer accountId){
        if(articleId==null || accountId==null)return RestBean.failure(401);
        ArticleDetailsDTO articleDetailsDTO = articleService.getArticle(articleId, accountId);
        if(articleDetailsDTO==null) return RestBean.failure(401);
        return RestBean.success(articleDetailsDTO);
    }

    /**
     * 获取文章详情页
     * @param accountId 用户ID
     * @return 文章对象
     */
    @GetMapping("/getUserArticle")
    public RestBean<List<ArticleDetailsDTO>> getUserArticle(@RequestParam("accountId") Integer accountId){
        if(accountId==null)return RestBean.failure(401);
        ArrayList<ArticleDetailsDTO> userArticles = articleService.getUserArticle( accountId);
        return RestBean.success(userArticles);
    }

    /**
     * 查询用户收藏过的文章
     * @param accountId 用户ID
     * @return  文章集合
     */
    @GetMapping("/getUserStarArticle")
    public RestBean<ArrayList<UserStarArticleDTO>> getUserStarArticle(@RequestParam("accountId") Integer accountId){
        if(accountId==null)return RestBean.failure(401);
        ArrayList<UserStarArticleDTO> userStarArticles = articleService.getUserStarArticle(accountId);
        return RestBean.success(userStarArticles);
    }


    @GetMapping("/deleteArticleStar")
    public RestBean<String> deleteArticleStar(@RequestParam("isStar") Integer isStar,
                                              @RequestParam("accountId") Integer accountId,
                                              @RequestParam("articleId") Integer articleId){
        if (isStar==null || accountId==null || articleId==null) return RestBean.failure(401,"请求参数不能为空");
        String s = articleService.deleteArticleStar(isStar,accountId,articleId);
        if(s != null) return RestBean.failure(401,s);
        return RestBean.success("删除成功");
    }

    /**
     * 根据用户Id查询 该用户点赞的文章数量
     * @param accountId 用户Id
     * @return  List集合
     */
    @GetMapping("/getLikeCount")
    public RestBean<Integer> getLikeCount(@RequestParam("accountId") Integer accountId){
        if(accountId == null) return RestBean.failure(0);
        int Count = articleService.getLikeCount(accountId);
        return RestBean.success(Count);
    }

    /**
     * 根据用户Id查询 该用户收藏的文章数量
     * @param accountId 用户Id
     * @return  List集合
     */
    @GetMapping("/getStarsCount")
    public RestBean<Integer> getStarsCount(@RequestParam("accountId") Integer accountId){
        if(accountId == null) return RestBean.failure(0);
        int Count = articleService.getStarCount(accountId);
        return RestBean.success(Count);
    }
    /**
     * 根据用户Id查询 该用户收藏的浏览过的文章数量
     * @param accountId 用户Id
     * @return  List集合
     */
    @GetMapping("/getSelectCount")
    public RestBean<Integer> getSelectCount(@RequestParam("accountId") Integer accountId){
        if(accountId == null) return RestBean.failure(0);
        int Count = articleService.getSelectCount(accountId);
        return RestBean.success(Count);
    }

    @GetMapping("/insertMessage")
    public RestBean<String> insertMessage(@RequestParam("accountId") Integer accountId,
                                          @RequestParam("articleId") Integer articleId){
        if (accountId == null || articleId == null) return RestBean.failure(401);
        if(articleService.insertMessage(accountId,articleId) != null) return RestBean.failure(400,"更新失败");
        return RestBean.success();
    }
}
