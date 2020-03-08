package io.kim_kong.covid_19_mask.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Store {
    /**
     * @param code - 식별 코드
     * @param name - 이름
     * @param addr - 주소
     * @param lat - 위도
     * @param lng - 경도
     * @param stock_t - 입고시간
     * @param stock_cnt - 입고 수량
     * @param sold_cnt - 판매수량
     * @param remain_cnt - 재고 수량
     * @param sold_out - 완파 여부(true 일 경우 판매 종료)
     * @param create_at - 데이터 생성 일자
     **/
    @SerializedName("code")
    @Expose
    lateinit var code: String

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("addr")
    @Expose
    lateinit var addr: String

    @SerializedName("lat")
    @Expose
    var lat: Float = 0.0000000f

    @SerializedName("lng")
    @Expose
    var lng: Float = 0.0000000f

    @SerializedName("stock_t")
    @Expose
    lateinit var stock_t: String

    @SerializedName("stock_cnt")
    @Expose
    var stock_cnt: Int = 0

    @SerializedName("sold_cnt")
    @Expose
    var sold_cnt: Int = 0

    @SerializedName("remain_cnt")
    @Expose
    var remain_cnt: Int = 0

    @SerializedName("sold_out")
    @Expose
    var sold_out: Boolean = false

    @SerializedName("created_at")
    @Expose
    lateinit var created_at: String
}