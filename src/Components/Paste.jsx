import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { removeFromPastes } from '../Reducer/pasteSlice';
import toast from 'react-hot-toast';
import { NavLink } from "react-router"
import './Paste.css'
import Edit from '../assets/Edit.png'
import View from '../assets/View.png'
import Delete from '../assets/Delete.png'
import Copy from '../assets/Copy.png'
// import Share from '../assets/Share.png'
import Date from '../assets/Date.png'

const Paste = () => {
  const pastes = useSelector((state) => state.paste.pastes);
  // console.log(pastes);
  const dispatch = useDispatch();
  const [searchTerm, setSearchTerm] = useState('');
  const filteredData = pastes.filter((paste) => paste.title.toLowerCase().includes(searchTerm.toLowerCase()))

  function handleDelete(pasteId) {
    dispatch(removeFromPastes(pasteId));
  }

  function handleCopy(paste) {
    navigator.clipboard.writeText(paste.Content)
    toast.success("Copied to Clipboard");
  }

  return (
    <div id='allPaste-container'>
      <input
        id='search'
        placeholder='Search here . . .'
        type='search'
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <h3>All Pastes</h3>

      {filteredData.length === 0 ? (
        <div id='No-Data'>No Data Found
        </div>
      ) : (
        <div>
          {filteredData.map((paste) => {
            return (
              <div id='pastes-box' key={paste?._id}>
                <div id='paste-title'>
                  {paste.title}
                </div>

                <div id='paste-Content'>
                  {paste.Content}
                </div>

                <div id='view-btn'>
                  <button className='btns'>
                    <NavLink to={`/?pasteId=${paste?._id}`}><img src={Edit} /></NavLink>
                  </button>
                  <button className='btns'>
                    <NavLink to={`/pastes/${paste?._id}`}><img src={View} /></NavLink>
                  </button>
                  <button className='btns' onClick={() => handleDelete(paste?._id)}><img src={Delete} /></button>
                  <button id='copy' className='btns' onClick={() => handleCopy(paste)}><img src={Copy} /></button>

                  {/* <button id='share' className='btns' > <img src={Share} /></button> */}
                </div>
                <div id='time'><img src={Date}/>{paste.createdAt}</div>
              </div>
            )
          })}
        </div>
      )}
    </div>
  )
}

export default Paste