package org.zerock.ocrpro.service;

import org.springframework.data.repository.query.Param;
import org.zerock.ocrpro.DAO.OCRDAO;
import org.zerock.ocrpro.vo.TableVO;

import java.util.List;

/**
 *         OCRAPI 사용후 데이터베이스에 넣기 위해 조작할 수있는 service 계층
 *         싱글톤 INSTANCE를 사용해서 사용하면 됨
 *         일단 DAO 계층까지만 존재하는데 DTO 계층도 필요하다고 한다면 추후에 수정하면 될 것 같음.
 */
public enum OCRService {

    INSTANCE;

    private OCRDAO ocrdao;

    OCRService(){
        ocrdao = new OCRDAO();
    }

    /**
     * 데이터베이스에 추가
     *
     * @param vo
     * @throws Exception
     */
    public void register(TableVO vo) throws Exception{
        ocrdao.insertToTable(vo);
    }

    /**
     * 데이터 베이스 수정
     *
     * @param vo
     * @throws Exception
     */
    public void modify(TableVO vo) throws Exception{
        ocrdao.modifyToTable(vo);
    }

    /**
     * 데이터베이스의 모든 객체 리턴
     *
     * @return
     * @throws Exception
     */
    public List<TableVO> listAll() throws Exception{

        List<TableVO> result = ocrdao.getTable();

        return result;
    }

    /**
     * 데이터베이스의 특정 객체 삭제
     *
     * @param vo
     * @throws Exception
     */
    public void remove(TableVO vo) throws Exception{
        ocrdao.deleteOne(vo.getNumber());
    }

    /**
     * 처음에 리스트를 받아와서 해당 객체가 존재하는지 존재하지 않는지 확인
     * true 일때는 객체가 존재하므로 modify
     * false 일때는 객체가 미존재하므로 register
     *
     * @param targetNum
     * @return
     * @throws Exception
     */
    public boolean isExist(int targetNum) throws Exception{
        try{
            ocrdao.getOne();
        }catch (Exception e){
            return false;
        }
        return true;
    }


}
