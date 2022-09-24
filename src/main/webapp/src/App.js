import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  const [message, setMessage] = useState("");
  useEffect(() => {
    fetch("commonApi/READ", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body : JSON.stringify({
        SQL_ID:"SqlSessionTest.getById",
        ID:"1"
        })
     })
     .then(response => response.text())
     .then(message => { setMessage(message);});
  },[])
 

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <h1 className="App-title">{message}</h1>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
