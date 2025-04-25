本專案為 Java 工程師線上作業，使用 Spring Boot 建立，實作幣別資料表的 CRUD 與 CoinDesk 匯率 API 的資料轉換。

📌 **專案功能**
* 幣別資料表 API（CRUD） `/currencies`：查詢、建立、修改、刪除幣別代碼與中文對照表
* 資料轉換 API `/api/exchange-rates`：回傳更新時間、幣別、中文名稱與匯率資訊

🧪 **測試內容**
* 資料轉換邏輯單元測試
* 幣別表 CRUD 測試
* 轉換後 API 測試

🗃️ **初始化 SQL**
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
