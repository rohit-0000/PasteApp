import React from 'react'
import toast from 'react-hot-toast';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import './ViewPaste.css'
import { NavLink } from 'react-router-dom'

import copy from '../assets/copy.png'
const ViewPaste = () => {
  const { id } = useParams();

  const allPastes = useSelector((state) => state.paste.pastes);
  const paste = allPastes.filter((p) => p._id === id)[0];
  function handleCopy(paste) {
    navigator.clipboard.writeText(paste.Content);
    toast.success("Copied to Clipboard");
  }

  return (
    <div id="view-Paste-container">
      <div id='view-paste-title'>
        <input id='title-input'
          type='text'
          disabled
          defaultValue={paste.title}
        />

        <NavLink id='Create-new'  to="/">Create New</NavLink>
      </div>

      <div id='view-Content'>
        <div id='Copy-area'>
          <div id='temp'>
            <span id='red' ></span>
            <span id='yellow'></span>
            <span id='green'></span>
          </div>
          <button id='copy-btn' onClick={() => handleCopy(paste)}><img src={copy} /></button>
        </div>
        <textarea id='content-input'
          disabled
          defaultValue={paste.Content}
        />
      </div>
    </div>
  )
}

export default ViewPaste