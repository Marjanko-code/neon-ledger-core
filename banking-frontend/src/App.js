import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

/**
 * NEON LEDGER: District 01 - Main Terminal Interface
 * Medior-level implementation:
 * - Fixed URL mapping (/ledger, /transfer)
 * - URLSearchParams for Spring @RequestParam compatibility
 * - Basic Auth integration
 */
function App() {
  const [balance, setBalance] = useState(5000.00);
  const [transactions, setTransactions] = useState([]);
  const [showExtra, setShowExtra] = useState(false);
  const [loading, setLoading] = useState(false);

  // AI Assistant State
  const [aiMsg, setAiMsg] = useState("CONNECTION_ESTABLISHED. DISTRICT_01_NODE ONLINE.");
  const [chatInput, setChatInput] = useState("");

  // Transaction Form State
  const [formData, setFormData] = useState({
    receiverName: '',
    receiverIban: '',
    amount: '',
    vs: '',
    ss: '',
    ks: '',
    description: ''
  });

  // Auth Header for Spring Security Basic Auth (Must match Java SecurityConfig)
  const authConfig = {
    auth: {
      username: 'admin',
      password: 'banka2026'
    }
  };

  /**
   * AI Logic
   */
  const askAi = (topic) => {
    const command = topic || chatInput.toLowerCase();

    if (command.includes("balance") || command === "STAV_KONTA") {
      setAiMsg(`CURRENT_LIQUIDITY: ${balance.toFixed(2)} CREDITS. STANDBY.`);
    } else if (command.includes("agent") || command === "SUPPORT") {
      setAiMsg("HUMAN_AGENTS_REPLACED. CONTACT EMERGENCY UPLINK: +421 900 666 001.");
    } else if (command.includes("security") || command === "ENCRYPTION") {
      setAiMsg("AES-256 GCM PROTOCOLS ACTIVE. ALL PACKETS ENCRYPTED.");
    } else {
      setAiMsg("COMMAND_NOT_RECOGNIZED. ATTEMPTING NEURAL RECOVERY...");
    }
    setChatInput("");
  };

  /**
   * FETCH LEDGER: Gets transaction history from /api/transactions/ledger
   */
  const fetchLedger = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/transactions/ledger', authConfig);
      // Backend vracia list transakcií, otočíme ho aby najnovšie boli hore
      setTransactions(res.data.reverse()); 
      
      // Voliteľné: Ak chceš dynamicky ťahať balance z backendu
      // const balRes = await axios.get('http://localhost:8080/api/transactions/balance/SK111222333', authConfig);
      // setBalance(balRes.data);
    } catch (err) {
      console.error("LEDGER_FETCH_ERROR:", err);
      setAiMsg("🚨 ALERT: BACKEND_UNREACHABLE. CHECK_SYSTEM_LOGS.");
    }
  };

  useEffect(() => {
    fetchLedger();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  /**
   * EXECUTE TRANSFER: Sends data to /api/transactions/transfer
   */
  const executeTransfer = async (e) => {
    e.preventDefault();

    // Validácia
    if (!formData.receiverIban || !formData.amount) {
      setAiMsg("🚨 CRITICAL_ERROR: MANDATORY_FIELDS_MISSING.");
      return;
    }

    setLoading(true);

    try {
      /**
       * DÔLEŽITÉ: Java backend používa @RequestParam. 
       * Preto neposielame JSON objekt, ale URLSearchParams.
       */
      const params = new URLSearchParams();
      params.append('from', 'SK111222333'); // Tvoj hlavný účet z DataInitializeru
      params.append('to', formData.receiverIban);
      params.append('amount', formData.amount);

      await axios.post('http://localhost:8080/api/transactions/transfer', params, authConfig);

      setAiMsg("TRANSACTION_AUTHORIZED. CREDITS_TRANSFERED_SUCCESSFULLY.");

      // Reset formulára a refresh dát
      setFormData({
        receiverName: '', receiverIban: '', amount: '',
        vs: '', ss: '', ks: '', description: ''
      });
      
      // Refreshneme zoznam transakcií priamo z DB
      fetchLedger();
      
      // Lokálne odpočítame balance pre okamžitý efekt
      setBalance(prev => prev - parseFloat(formData.amount));

    } catch (err) {
      console.error("TRANSFER_ERROR:", err);
      const errorMsg = err.response?.data || "TRANSMISSION_FAILURE";
      setAiMsg(`🚨 CRITICAL_ERROR: ${errorMsg}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="neon-wrapper">
      <header className="cyber-header">
        <div className="logo">NEON<span>LEDGER</span></div>
        <div className="balance-box">
          <small>AVAILABLE_LIQUIDITY</small>
          <div className="amount">{balance.toFixed(2)} CR</div>
        </div>
      </header>

      <main className="main-grid">
        <section className="card terminal">
          <h2 className="glitch" data-text="TRANSFER_TERMINAL">TRANSFER_TERMINAL</h2>
          <form onSubmit={executeTransfer} noValidate>
            <div className="input-group">
              <input
                placeholder="RECEIVER_IDENTITY (Optional)"
                value={formData.receiverName}
                onChange={e => setFormData({...formData, receiverName: e.target.value})}
              />
              <input
                placeholder="DESTINATION_IBAN (e.g. SK999888777)"
                value={formData.receiverIban}
                onChange={e => setFormData({...formData, receiverIban: e.target.value})}
              />
              <input
                type="number"
                placeholder="CREDIT_AMOUNT"
                value={formData.amount}
                onChange={e => setFormData({...formData, amount: e.target.value})}
              />
            </div>

            <button
              type="button"
              className="btn-secondary"
              onClick={() => setShowExtra(!showExtra)}
            >
              {showExtra ? "[-] CLOSE_EXTENDED_PROTOCOLS" : "[+] OPEN_EXTENDED_PROTOCOLS"}
            </button>

            {showExtra && (
              <div className="extra-fields animate-slide">
                <input placeholder="VS (Variable)" value={formData.vs} onChange={e => setFormData({...formData, vs: e.target.value})} />
                <input placeholder="SS (Specific)" value={formData.ss} onChange={e => setFormData({...formData, ss: e.target.value})} />
                <input placeholder="KS (Constant)" value={formData.ks} onChange={e => setFormData({...formData, ks: e.target.value})} />
                <input placeholder="LOG_DESCRIPTION" className="full-width" value={formData.description} onChange={e => setFormData({...formData, description: e.target.value})} />
              </div>
            )}

            <button type="submit" className="btn-main" disabled={loading}>
              {loading ? "PROCESSING..." : "EXECUTE_TRANSFER"}
            </button>
          </form>
        </section>

        <section className="card ledger">
          <h2>LEDGER_HISTORY_LOG</h2>
          <div className="history-list">
            {transactions.length === 0 ? (
              <div className="empty-state">NO_DATA_FOUND_IN_NODE</div>
            ) : (
              transactions.map((t, index) => (
                <div key={t.id || index} className="history-item animate-fade">
                  <div className="t-info">
                    <span className="t-name">{t.receiverIban}</span>
                    <span className="t-date">{t.createdAt ? new Date(t.createdAt).toLocaleString() : "NOW"}</span>
                  </div>
                  <span className="t-amount out">-{parseFloat(t.amount).toFixed(2)} CR</span>
                </div>
              ))
            )}
          </div>
        </section>
      </main>

      <footer className="ai-console">
        <div className="ai-header">NEURAL_ASSISTANT_V4</div>
        <div className="ai-body">
          <div className="ai-text">{aiMsg}</div>
        </div>
        <div className="quick-actions">
          <span className="tag" onClick={() => askAi('STAV_KONTA')}>[ CHECK_BALANCE ]</span>
          <span className="tag" onClick={() => askAi('ENCRYPTION')}>[ ENCRYPTION_STATUS ]</span>
          <span className="tag" onClick={() => askAi('SUPPORT')}>[ UPLINK_AGENT ]</span>
        </div>
        <div className="ai-input">
          <input
            value={chatInput}
            onChange={e => setChatInput(e.target.value)}
            placeholder="Enter command..."
            onKeyPress={(e) => e.key === 'Enter' && askAi()}
          />
          <button onClick={() => askAi()}>RUN</button>
        </div>
      </footer>
    </div>
  );
}

export default App;