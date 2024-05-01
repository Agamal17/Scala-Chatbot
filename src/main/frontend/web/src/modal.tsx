import React, { useState } from 'react';

// @ts-ignore
const Modal = ({ isOpen, onClose, onCreate }) => {
    const [chatName, setChatName] = useState('');

    const handleInputChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setChatName(event.target.value);
    };

    const handleCreate = () => {
        onCreate(chatName); // Call the provided onCreate function with chat name
        onClose(); // Close the modal after creating the chat
    };

    return (
        <div className={`modal ${isOpen ? 'active' : ''}`}>
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Create Chat</h2>
                    <button onClick={onClose}>
                        Close
                    </button>
                </div>
                <div className="modal-body">
                    <input
                        type="text"
                        placeholder="Chat Name"
                        value={chatName}
                        onChange={handleInputChange}
                    />
                </div>
                <div className="modal-footer">
                    <button type="button" className="btn btn-primary" onClick={handleCreate}>
                        Create
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Modal;