import React, { useEffect, useState } from 'react'
import { useSearchParams } from "react-router";
import { useDispatch, useSelector } from 'react-redux';
import { addToPastes, updateToPastes } from '../Reducer/pasteSlice';

import './Home.css'
import toast from 'react-hot-toast';
import copy from '../assets/copy.png'
const Home = () => {
    const [title, setTitle] = useState('');
    const [value, setValue] = useState('');
    const [searchParams, setSearchParams] = useSearchParams();
    const pasteId = searchParams.get("pasteId");
    const dispatch = useDispatch();

    const allPastes = useSelector((state) => state.paste.pastes);
    useEffect(() => {
        if (pasteId) {
            const paste = allPastes.find((p) => p._id === pasteId);
            setTitle(paste.title);
            setValue(paste.Content);
        } else {
            setTitle('');
            setValue('');
        }
    }, [pasteId])

    
    function creatPaste() {
        if (title != '' || value!='') {
            const paste = {
                title: title,
                Content: value,
                _id: pasteId || Date.now().toString(36),
                // createdAt: new Date().toISOString,
                createdAt:new Date().toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' }).replace(/ /g, '-')
            }

            if (pasteId) {
                //update
                dispatch(updateToPastes(paste));
            } else {
                //create
                dispatch(addToPastes(paste));
            }
            //after creation or updation
            setTitle('');
            setValue('');
            setSearchParams({});
        }else{
            toast.error('Write Something .. ');
        }

    }
    function handleCopy() {
        navigator.clipboard.writeText(value);
        toast.success("Copied to Clipboard");
    }
    return (
        <div id="home-container">
            <div id='title'>
                <input id='title-input'
                    required
                    type='text'
                    placeholder='Enter title here'
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />

                <button id='submit-btn' onClick={creatPaste}>
                    {
                        pasteId ? 'Update Paste' : 'Create Paste'
                    }
                </button>
            </div>
            <div id='content'>
                <div id='Copy-area'>
                    <div id='temp'>
                        <span id='red' ></span>
                        <span id='yellow'></span>
                        <span id='green'></span>
                    </div>
                    <button id='copy-btn' onClick={handleCopy}><img src={copy} />
                    </button>
                </div>
                <textarea
                    id='content-input'
                    value={value}
                    placeholder='Enter content here'
                    onChange={(e) => setValue(e.target.value)}
                />
            </div>
        </div>
    )
}

export default Home

