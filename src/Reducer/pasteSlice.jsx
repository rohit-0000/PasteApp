import { createSlice } from '@reduxjs/toolkit';
import toast from 'react-hot-toast';

const initialState = {
    //if pastes is available then convert/parse into json otherwise create empty list
    pastes:localStorage.getItem("pastes")
    ? JSON.parse(localStorage.getItem("pastes"))
    : []
}

export const pasteSlice =createSlice({
    name: 'paste',
    initialState,
    reducers:{
        addToPastes: (state,action) => {
            const paste=action.payload;
            if (state.pastes.find(p => p.title === paste.title)) {
                toast.error("Already Exist this title");
            } 
            else{
                state.pastes.push(paste); //adding to pastes
                localStorage.setItem("pastes",JSON.stringify(state.pastes));//adding to local storage
                toast.success("Paste Created Sucessfully .");
            }

        },
        updateToPastes: (state,action) => {
            const paste=action.payload;
            const index=state.pastes.findIndex((item)=>item._id===paste._id);

            if(index>=0){
                state.pastes[index]=paste;

                localStorage.setItem("pastes",JSON.stringify(state.pastes));
                toast.success("Paste updated");
            }
        },
        resetAllPastes: (state,action) => {
            state.pastes = [];
            localStorage.removeItem('pastes');
        },
        removeFromPastes: (state,action) => {
            const pasteId = action.payload;
            const index = state.pastes.findIndex((item)=>item._id === pasteId);

            if(index>=0){
                state.pastes.splice(index,1);//it will remove 1 paste at index
                localStorage.setItem("pastes",JSON.stringify(state.pastes));
                toast.success("Paste deleted");
            }
        }
    } 
})

export const {addToPastes,updateToPastes,resetAllPastes,removeFromPastes} = pasteSlice.actions
export default pasteSlice.reducer