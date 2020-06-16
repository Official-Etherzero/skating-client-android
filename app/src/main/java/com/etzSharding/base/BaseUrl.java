package com.etzSharding.base;

import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;

import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.tx.Contract;

import java.util.Locale;

public class BaseUrl {
    public final static String HTTP_ADDRESS = "https://easyetz.io/etzq/";
    public final static String HTTP_TOKEN_LISTURL = HTTP_ADDRESS+"api/v1/getTokenList";
    public final static String HTTP_UPDATE_RATES = "https://bitpay.com/rates";
    public final static String HTTP_ETZ_RATE = "https://api.coinmarketcap.com/v2/ticker/2843/?convert=BTC&limit=10&structure=array";//seek价格对usdt
    public final static String HTTP_ETH_RPC = "https://api.breadwallet.com";
    public static String node;
    public static String getEthereumRpcUrl() {

        if (Util.isNullOrEmpty(node)) {
            node = SharedPrefsUitls.getInstance().getCurrentETZNode();
            if (Util.isNullOrEmpty(node)) {
                String languageCode = Locale.getDefault().getLanguage();//手机语言
//                node = languageCode.equalsIgnoreCase("zh") ? "http://159.65.133.190:8545" : "https://sg.etznumberone.com:443";
                node = "http://13.251.6.203:8545";
                SharedPrefsUitls.getInstance().putCurrentETZNode( node);
            }
        }
        return  "https://sg.etznumberone.com:443";

    }

    /**
     * etz代币余额接口
     * @param address
     * @param contractAddress
     * @return
     */
    public static String getEtzTokenBalance(String address, String contractAddress) {

        return HTTP_ADDRESS + "api/v1/gettokenBlance?address=" + address + "&contractaddress=" + contractAddress;
//
    }

    /**
     * ethRPC
     * @return
     */
    public static String getEthRpcUrl() {
        return HTTP_ETH_RPC+ "/ethq/mainnet/proxy";
    }
    /**
     * eth代币余额接口
     * @param address
     * @param contractAddress
     * @return
     */
    public static String getEthTokenBalance(String address, String contractAddress) {
              return HTTP_ETH_RPC+  "/ethq/mainnet/query?" + "module=account&action=tokenbalance"
                + "&address=" + address + "&contractaddress=" + contractAddress;
    }

    /**
     * 获取powetUTL
     * @param address
     * @return
     */
    public static String getPowerUrl(String address){
        return HTTP_ADDRESS + "api/v1/getPower?address="+address;
    }


    /**
     * 获取ETZ交易记录Url
     * @param address
     * @return
     */
    public static String getETZTransactionsUrl(String address){
        return HTTP_ADDRESS +"api/v1/getEtzTxlist?address="+address;
    }
    /**
     * 获取ETZ代币交易记录Url
     * @param address
     * @return
     */
    public static String getETZTokenLogsUrl(String address, String contract, String fromBlock){
        String add="0x000000000000000000000000"+address.substring(2,address.length());
        return  "https://etzscan.com/publicAPI?module=logs&action=getLogs&" + (null == contract ? "" : ("address=" + contract+"&")) + "&fromBlock="+fromBlock+"&topic_oprs=0_and,1_2_or"
                +"&topics=0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef,"+add+"," + add+"&apikey=YourApiKeyToken";
    }
    /**
     * 获取ETh交易记录Url
     * @param address
     * @return
     */
    public static String getEThTransactionsUrl(String address){

//        return HTTP_ETH_RPC+  "/ethq/mainnet/query?module=account&action=txlist&address=" + address;
        return HTTP_ETH_RPC+  "/ethq/mainnet/query?module=account&action=txlist&address=0x4baf9ce9ab1e60dd43c1db7bd7f1bf96fd6a1a80" ;
    }
    /**
     * 获取ETh代币交易记录Url
     * @param address
     * @return
     */
    public static String getEThTokenLogsUrl(String address, String contract, String event){

        return HTTP_ETH_RPC+ "/ethq/mainnet/query?"
                + "module=logs&action=getLogs"
                + "&fromBlock=0&toBlock=latest"
                + (null == contract ? "" : ("&address=" + contract))
                + "&topic0=" + event
                + "&topic1=" + address
                + "&topic1_2_opr=or"
                + "&topic2=" + address;
    }

    //請求版本更新
    public static String versionCheekUrl(){
        return  "https://app.seekchain.org/airdropapi/api/v1/versionCheck";
    }

}
