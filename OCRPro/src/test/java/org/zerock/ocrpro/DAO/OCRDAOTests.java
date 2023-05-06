package org.zerock.ocrpro.DAO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.ocrpro.ocr.OCRAPI;
import org.zerock.ocrpro.service.OCRService;
import org.zerock.ocrpro.vo.ListToVO;
import org.zerock.ocrpro.vo.TableVO;

import java.util.ArrayList;
import java.util.List;


/**
 * OCRDAOTest 코드 작성
 * listToVO, ocrService를 각각 싱글톤으로 호출해 놓고 각각의 기능들에 대해서 테스트 코드 작성 후 정상 작동 확인
 */
public class OCRDAOTests {

    private OCRAPI ocrapi = OCRAPI.getInstance();

    private ListToVO listToVO = ListToVO.INSTANCE;

    private OCRService ocrService = OCRService.INSTANCE;

    /**
     * ocrservice의 listall 함수 test
     * @throws Exception
     */
    @Test
    public void testGetTable() throws Exception{
        List<TableVO> result = ocrService.listAll();

        for (TableVO vo : result){
            System.out.println(vo);
        }
    }

    /**
     *ocrservice의 register함수 test
     * 해당 리스트가 비어있으면 continue
     * 해당 함수에서 NumberFormatException이 발생하면 객체로 인식하지 않고 continue
     * @throws Exception
     */
    @Test
    public void testInsertTable() throws Exception{
        List<List<String>> list = ocrapi.getArray("/Users/kimbyungchan/Downloads/OCRTABLE1-1.png");
        for(List<String> util : list){
            if(util.isEmpty()){
                continue;
            }

            try{
                Integer.valueOf(util.get(0));
            }catch (NumberFormatException e){
                continue;
            }
            TableVO vo = listToVO.listToVO(util);
            ocrService.register(vo);
        }


        List<TableVO> result = ocrService.listAll();

        for (TableVO vo : result){
            System.out.println(vo);
        }
    }


    /**
     * ocrService의 modify 기능 test
     * 하나의 list를 만들어서 테스트함
     * @throws Exception
     */
    @Test
    public void testModifyTable() throws Exception{
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("BL11");
        list.add("중국");
        list.add("고추");
        list.add("2022-08-06");
        list.add("2023-09-01");
        list.add("40");
        list.add("20");
        list.add("1,354.92");
        list.add("24,390,000");
        list.add("null");

        TableVO vo = listToVO.listToVO(list);

        ocrService.modify(vo);

        List<TableVO> result = ocrService.listAll();

        for (TableVO vo1 : result){
            System.out.println(vo1);
        }
    }

    /**
     * Ocrservice에서 remove함수 test
     * @throws Exception
     */
    @Test
    public void testDeleteOne() throws Exception{
        TableVO vo = TableVO.builder()
                .number(5).build();

        ocrService.remove(vo);

        List<TableVO> result = ocrService.listAll();

        for (TableVO vo1 : result){
            System.out.println(vo1);
        }
    }

    /**
     * Ocrservice에서 inexist함수 test
     * isexist 함수가 true 일 경우는 해당 객체가 존재하는 것이므로 modify
     * isexist 함수가 false 일 경우는 해당 객체가 미존재하므로 register
     *
     * @throws Exception
     */
    @Test
    public void testIsExist() throws Exception{
        List<String> list = new ArrayList<>();
        list.add("6");
        list.add("BL11");
        list.add("중국");
        list.add("고추");
        list.add("2022-08-06");
        list.add("2023-09-01");
        list.add("40");
        list.add("30");
        list.add("1,354.92");
        list.add("24,390,000");
        list.add("null");

        TableVO vo = listToVO.listToVO(list);

        if(ocrService.isExist(vo.getNumber())){
            ocrService.modify(vo);
        }else{
            ocrService.register(vo);
        }
    }
}
