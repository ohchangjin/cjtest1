package etc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 이벤트용 랜덤 상품 조회
 * @author leehyunkeun
 *
 */
public class RandomUtil {
	
	private static Log log = LogFactory.getFactory().getInstance(RandomUtil.class.getName());	
	private static final int MAX_PERSENT = 100;
	
	/**
	 * E01 : 퍼센트 오류, E02 :리스트 오류, E99 : 시스템 에러
	 * @param _plist
	 * @return
	 */
	public static String getRanDomProduct(List<ProductInfo> _plist) throws Exception {
		int sum_persent = 0;
		
		try {
			if(_plist != null && _plist.size() > 0) {
				// 퍼센트 체크
				for(ProductInfo xx : _plist) {
					sum_persent += xx.getPercentage();
				}
				log.debug("sum_persent:"+sum_persent);
				
				// MXX퍼센트와 맞는지 체크
				if(sum_persent == MAX_PERSENT) {
					// 상품 초기화
					LinkedList<String> _initp = productInit(_plist);
					// 상품 믹스(옵션)
					LinkedList<String> _mixp = productMix(_initp);
					// 랜덤 상품 조회
					return getProductRandom(_mixp);
				} else {
					return "E01";
				}
			} else {
				return "E02";
			}
		} catch(Exception e) {
			return "E99";
		}
	}
	
	/**
	 * 랜덤 상품 조회 함
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static String getProductRandom(LinkedList<String> result) throws Exception {
		Random random = new Random();
		return result.get(random.nextInt(result.size()));
	}
	
	/**
	 * 상품 랜덤으로 썩음
	 * @param _initp
	 * @return
	 */
	public static LinkedList<String> productMix(LinkedList<String> _initp) throws Exception {
		LinkedList<String> _mixp = new LinkedList<String>();
		
		for(int xx = MAX_PERSENT ; xx > 0; xx--) {
			Random random = new Random();
			int _idx = random.nextInt(xx);
			
			_mixp.add(_initp.get(_idx));
			_initp.remove(_idx);
			
			log.debug("_initp size"+_initp.size()+",_idx:"+_idx);
		} 
		log.info("_mixp size:"+_mixp.size());
		log.info("_mixp values:"+_mixp);
		return _mixp;
	}
	
	/**
	 * 상품 초기화
	 * @param _plist
	 * @return
	 */
	public static LinkedList<String> productInit(List<ProductInfo> _plist) throws Exception {
		LinkedList<String> _initp = new LinkedList<String>();
		
		// 비율에 따라 상품 초기 세팅
		for(ProductInfo xx : _plist) {
			int persent = xx.getPercentage();
			for(int yy = 0; yy < persent; yy++) {
				_initp.add(xx.getCode());
			}
		}
		log.debug("_initp size:"+_initp.size());
		log.debug("_initp values:"+_initp);
		return _initp;
	}
	
	public static void main(String[] args) {
		List<ProductInfo> _plist = new ArrayList<ProductInfo>();
		
		ProductInfo productInfo01 = new ProductInfo();
		productInfo01.setCode("CU01");
		productInfo01.setPercentage(50);
		
		ProductInfo productInfo02 = new ProductInfo();
		productInfo02.setCode("CU02");
		productInfo02.setPercentage(45);
		
		ProductInfo productInfo03 = new ProductInfo();
		productInfo03.setCode("CU03");
		productInfo03.setPercentage(5);
		
		_plist.add(productInfo01);
		_plist.add(productInfo02);
		_plist.add(productInfo03);
		
		try {
			log.debug("result:" + getRanDomProduct(_plist));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * 상품 VO
 * @author leehyunkeun
 *
 */
class ProductInfo {
	private String code = "";		// 상품 코드
	private int percentage = 0;		// 상품 비율
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	@Override
	public String toString() {
		return "ProductInfo [code=" + code + ", percentage=" + percentage + "]";
	}
}