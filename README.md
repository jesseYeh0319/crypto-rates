## 專案：Java 工程師線上作業 - Spring Boot 幣別資料表 CRUD 與 CoinDesk 匯率 API 轉換

**專案功能：**

* **幣別資料表 API（CRUD） /currencies：**
    * 查詢幣別代碼與中文對照表
    * 建立幣別代碼與中文對照表
    * 修改幣別代碼與中文對照表
    * 刪除幣別代碼與中文對照表
* **資料轉換 API /api/exchange-rates：**
    * 回傳更新時間
    * 回傳幣別代碼
    * 回傳中文名稱
    * 回傳匯率資訊

**測試內容：**

* 資料轉換邏輯單元測試
* 幣別表 CRUD 測試
* 轉換後 API 測試

**初始化 SQL：**

```sql
CREATE TABLE CURRENCY (
    CODE VARCHAR(10) PRIMARY KEY NOT NULL,
    SYMBOL VARCHAR(10),
    EN_NAME VARCHAR(100),
    ZH_NAME VARCHAR(100)
);

INSERT INTO CURRENCY (CODE, SYMBOL, EN_NAME, ZH_NAME) VALUES
('USD', '$', 'United States Dollar', '美元'),
('EUR', '€', 'Euro', '歐元');
