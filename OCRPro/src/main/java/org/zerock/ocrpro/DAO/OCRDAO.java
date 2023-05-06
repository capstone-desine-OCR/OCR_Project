package org.zerock.ocrpro.DAO;

import lombok.Cleanup;
import org.zerock.ocrpro.connection.ConnectionUtil;
import org.zerock.ocrpro.vo.TableVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *       OCR 관련 쿼리 작성
 *       일단 여기에 존재하는 쿼리들은 여러개의 객체들을 한번에 넣는 것들만 가능
 *
 *       Connection을 통해서 커넥션 연결
 *       Prepared~을 통해서 쿼리안에 넣어서 적용 가능
 *
 *       lombok 라이브러리 사용 설치 필요!
 */
public class OCRDAO {

    /**
     * sql쿼리의 결과값을 TableVO로 변환 시켜서 테이블을 List 형식으로 리턴
     *
     * @return
     * @throws Exception
     */
    public List<TableVO> getTable() throws Exception {
        List<TableVO> result_table = new ArrayList<>();

        @Cleanup
        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("select * from ocrtable1");
        @Cleanup
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            TableVO vo = TableVO.builder()
                    .number(resultSet.getInt("num"))
                    .code(resultSet.getString("code"))
                    .origin(resultSet.getString("origin"))
                    .cultivar(resultSet.getString("cultivar"))
                    .inDate(resultSet.getDate("indate").toLocalDate())
                    .outDate(resultSet.getDate("outdate").toLocalDate())
                    .weight(resultSet.getInt("weight"))
                    .count(resultSet.getInt("count"))
                    .price(resultSet.getString("price"))
                    .won(resultSet.getString("won"))
                    .extra(resultSet.getString("extra"))
                    .build();

            result_table.add(vo);
        }
        return result_table;
    }

    /**
     * sql 쿼리의 결과값을 TableVO 로 변환시켜서 리턴
     *
     * @return
     * @throws Exception
     */
    public TableVO getOne() throws Exception{

        @Cleanup
        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement("select * from ocrtable1");
        @Cleanup
        ResultSet resultSet = preparedStatement.executeQuery();

        TableVO vo = TableVO.builder()
                .number(resultSet.getInt("num"))
                .code(resultSet.getString("code"))
                .origin(resultSet.getString("origin"))
                .cultivar(resultSet.getString("cultivar"))
                .inDate(resultSet.getDate("indate").toLocalDate())
                .outDate(resultSet.getDate("outdate").toLocalDate())
                .weight(resultSet.getInt("weight"))
                .count(resultSet.getInt("count"))
                .price(resultSet.getString("price"))
                .won(resultSet.getString("won"))
                .extra(resultSet.getString("extra"))
                .build();

        return vo;
    }


    /**
     *       하나의 객체 List에 대해서 db에 직접적으로 넣어짐
     *       문제점 - 만약에 list가 잘못된 정보를 주거나 위치가 바뀌게 되면 잘 못 들어갈 가능성이 높음.
     *       위치를 외워서 넣는 방식이라서 표를 고정시켜야함.
     *
     * @param vo
     * @throws Exception
     */
    public void insertToTable(TableVO vo) throws Exception {

        String query = "INSERT INTO OCRTABLE1 (num, code, origin, cultivar, indate, outdate, weight, count, price, won, extra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        @Cleanup
        Connection connection = ConnectionUtil.INSTANCE.getConnection();

        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        try {
            preparedStatement.setInt(1, vo.getNumber());
            preparedStatement.setString(2, vo.getCode());
            preparedStatement.setString(3, vo.getOrigin());
            preparedStatement.setString(4, vo.getCultivar());
            preparedStatement.setDate(5, Date.valueOf(vo.getInDate()));
            preparedStatement.setDate(6, Date.valueOf(vo.getOutDate()));
            preparedStatement.setInt(7, vo.getWeight());
            preparedStatement.setInt(8, vo.getCount());
            preparedStatement.setString(9, vo.getPrice());
            preparedStatement.setString(10, vo.getWon());
            preparedStatement.setString(11, vo.getExtra());

            preparedStatement.executeUpdate();
        } catch (NumberFormatException e) {
            System.out.println("형식에 맞지 않습니다.");
        }
    }

    /**
     * 처음에 시작할 때 존재하는지 확인 후에 만약에 존재한다면 해당 파일을 수정해야 하는 것으로 인식하고 수정 실행.
     *
     * @param vo
     * @throws Exception
     */
    public void modifyToTable(TableVO vo) throws Exception {

        String query = "UPDATE OCRTABLE1 SET weight = ?, count = ?, price = ?, won = ? where num = ?";

        @Cleanup
        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        try {
            preparedStatement.setInt(1, vo.getWeight());
            preparedStatement.setInt(2, vo.getCount());
            preparedStatement.setString(3, vo.getPrice());
            preparedStatement.setString(4, vo.getWon());
            preparedStatement.setInt(5, vo.getNumber());

            preparedStatement.executeUpdate();
        } catch (NumberFormatException e) {
            System.out.println("형식에 맞지 않습니다.");
        }
    }

    /**
     * 해당 객체 데이터베이스 테이블에서 삭제
     *
     * @param targetNum
     * @throws Exception
     */
    public void deleteOne(int targetNum) throws Exception {
        String query = "delete from OCRTABLE1 where num = ?";

        @Cleanup
        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, targetNum);

        preparedStatement.executeUpdate();
    }
}
