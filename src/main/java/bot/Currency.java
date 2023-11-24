package bot;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Currency {
    private String title;
    private String code;
    @SerializedName("cb_price")
    private String cbPrice;
    @SerializedName("nbu_buy_price")
    private String nbuBuyPrice;
    @SerializedName("nbu_cell_price")
    private String nbuCellPrice;


}
