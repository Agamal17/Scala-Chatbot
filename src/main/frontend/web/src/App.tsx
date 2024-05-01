import React, {useEffect, useState} from 'react';
import './App.css';
import Sidebar from "./sidebar";
import ChatContainer from "./chatContainer";
import Modal from "./modal";
import EUI from './EUI.png';
import CIS from './CIS.png';
// @ts-ignore
import teaser from './teaser.mp4'
// @ts-ignore
import axios from 'axios';

class Chat{
  ID: number;
  Name: string;
  constructor(ID: number, Name: string) {
    this.ID = ID;
    this.Name = Name;
  }
}

function App() {
  const [inputValue, setInputValue] = useState('');
  const [messages, setMessages] = useState([]);
  const [chats, setChats] = useState([]);
  const [curChat, setCurChat] = useState(null);

  const [isOpen, setIsOpen] = useState(false);
  const openModal = () => setIsOpen(true);
  const closeModal = () => setIsOpen(false);

  useEffect(() => {
    getChatNames()
  }, [])


  const sendQuery = (query: string, ID: number) => {
    const data = {
      id: ID,
      msg: query
    }

    axios.post(`http://localhost:4000/send`, data).then(res =>
        // @ts-ignore
        setMessages([...messages, query, res.data])
    ).catch( r =>
        console.log(r)
    )
  }

  const getChatNames = () => {
    axios.get('http://localhost:4000/chats').then(res => {
      let chatList = [];
      for (let i = 0;i<res.data.length;i++) {
        let x = new Chat(i+1, res.data[i]);
        chatList.push(x);
      }
      // @ts-ignore
      setChats(chatList)
    })
  }

  const getChat = (Name: string, ID: number) => {
    axios.get(`http://localhost:4000/fetch/${ID}`).then(res => {
      setMessages(res.data)
      let x = new Chat(ID, Name)
      // @ts-ignore
      setCurChat(x)
        }
    )
  }

  const handleCreateChat = (chatName: string) => {
    const data = {
      name: chatName
    }

    axios.post(`http://localhost:4000/create`, data).then(res =>
        // @ts-ignore
        getChatNames()
    ).catch( r =>
        console.log(r)
    )
  };


  return (
      <>
        <video src={teaser} autoPlay muted loop playsInline id={'video'}/>
        <div className="app-container">
          {isOpen && <Modal isOpen={isOpen} onClose={closeModal} onCreate={handleCreateChat} />}
          <Sidebar>
            <img className={"image-frame"} src={EUI} />
            <button onClick={() => openModal()} className={"red-button"}>Create New Chat</button>
            {chats.map((e) =>
                <div className={'button-container'}>
                <button  className={"brown-button"} onClick={
                  // @ts-ignore
                  (evt) => getChat(e.Name, e.ID)
                }>{
                  // @ts-ignore
                  e.Name
                }</button>
                  <button className={'close-button'}>
                    <span>X</span>
                  </button>
                </div>
            )}
          </Sidebar>

          <div className="chat-content"> {/* New container for chat area */}
            <ChatContainer>
              <div className="chat-message-box">
                {messages.map((e, idx) => (
                    <div
                        className={ idx%2 ? 'message-cloud received' : 'message-cloud sent'}
                    >
                      <p>{e}</p>
                    </div>
                ))}
              </div>
            </ChatContainer>
            <div className="input-container">
              <input
                  type="text"
                  value={inputValue}
                  onChange={(e) => setInputValue(e.target.value)}
              />
              <button onClick={() => {
                if (curChat === null) return
                if (inputValue !== '') {
                  // @ts-ignore
                  sendQuery(inputValue, curChat.ID)
                  // @ts-ignore
                  setInputValue('');
                }
              }}>Send</button>
            </div>
          </div>
        </div>
      <div className={"signature-container"}>
        <div>
        <p className="signature-text">Ahmed Gamal</p>
        <p className="signature-text">Seif Amr</p>
        <p className="signature-text">Mario George</p>
        <p className="signature-text">Youssef Nagui</p>
        </div>
        <img className={"image-frame"} src={CIS} height={50}/>
        <p className="faculty-text">Faculty of Computing and Information Sciences</p>
      </div>
      </>
  );
}

export default App;