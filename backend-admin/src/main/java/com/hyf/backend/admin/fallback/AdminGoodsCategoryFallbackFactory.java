package com.hyf.backend.admin.fallback;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.admin.feign.AdminGoodsCategoryClient;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsCategoryVO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Slf4j
public class AdminGoodsCategoryFallbackFactory implements FallbackFactory<AdminGoodsCategoryClient> {
    @Override
    public AdminGoodsCategoryClient create(Throwable cause) {
        return new AdminGoodsCategoryClient() {
            @Override
            public ResponseVO createGoodsCategory(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
                log.error("调用商品类目服务出错啦...params: {}", JSON.toJSONString(createGoodsCategoryDTO), cause);
                return ResponseVO.error(cause.getMessage());
            }

            @Override
            public ResponseVO findPageByQuery(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO) {
                log.error("调用商品类目服务出错啦...params: {}", JSON.toJSONString(queryGoodsCategoryDTO), cause);
                return ResponseVO.error(cause.getMessage());
            }

            @Override
            public ResponseVO updateGoodsCategory(AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO) {
                log.error("调用商品类目服务出错啦...params: {}", JSON.toJSONString(updateGoodsCategoryDTO), cause);
                return ResponseVO.error(cause.getMessage());
            }

            @Override
            public ResponseVO listLevel1() {
                log.error("调用商品类目服务出错啦...", cause);
                return ResponseVO.error(cause.getMessage());
            }

            @Override
            public ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel2() {
                return null;
            }
        };
    }
}
