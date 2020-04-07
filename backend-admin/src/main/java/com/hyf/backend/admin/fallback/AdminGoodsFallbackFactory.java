package com.hyf.backend.admin.fallback;

import com.hyf.backend.admin.feign.AdminGoodsCategoryClient;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.common.vo.PageVO;
import com.hyf.backend.goods.admin.vo.AdminGoodsCategoryVO;
import com.hyf.backend.goods.dto.AdminCreateGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminQueryGoodsCategoryDTO;
import com.hyf.backend.goods.dto.AdminUpdateGoodsCategoryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import feign.hystrix.FallbackFactory;

/**
 * @Author: Elvis on 2020/4/6
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class AdminGoodsFallbackFactory implements FallbackFactory<AdminGoodsCategoryClient> {
    @Override
    public AdminGoodsCategoryClient create(Throwable cause) {
        return new AdminGoodsCategoryClient() {
            @Override
            public ResponseVO<AdminGoodsCategoryVO> createGoodsCategory(AdminCreateGoodsCategoryDTO createGoodsCategoryDTO) {
                return null;
            }

            @Override
            public ResponseVO<PageVO<AdminGoodsCategoryVO>> findPageByQuery(AdminQueryGoodsCategoryDTO queryGoodsCategoryDTO) {
                return null;
            }

            @Override
            public ResponseVO<AdminGoodsCategoryVO> updateGoodsCategory(AdminUpdateGoodsCategoryDTO updateGoodsCategoryDTO) {
                return null;
            }

            @Override
            public ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel1() {
                return null;
            }

            @Override
            public ResponseVO<ListVO<AdminGoodsCategoryVO>> listLevel2() {
                return null;
            }
        };
    }
}
