import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import toast from 'react-hot-toast';
import axios from 'axios';


const initialState = {
    pastes: [],
    user:{},
    loading:false,
};

const API_URL = import.meta.env.VITE_API_URL;
export const fetchPastes = createAsyncThunk(
    'paste/fetchPastes',
    
        async () => {
            const token = localStorage.getItem('token');
            try{
            const response = await axios.get(`${API_URL}/paste`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            )
            return response.data;
        }
        catch(error){
            throw error;
        }
    }
);

export const fetchUser =createAsyncThunk('paste/fetchUser',async ()=>{
    const token=localStorage.getItem('token');
    const response=await axios.get(`${API_URL}/user/detail`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    )
    return response.data;
});

export const addPaste = createAsyncThunk('paste/addPaste',
    async (paste) => {
        const token = localStorage.getItem('token');
        const response = await axios.post(`${API_URL}/paste`, paste, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
        );
        
        return response.data;
    }
)



export const updatePaste = createAsyncThunk('paste/updatePaste', async (paste) => {
    const token = localStorage.getItem('token');
    const response = await axios.put(`${API_URL}/paste/${paste._id}`, paste, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    return response.data;
})

export const updateUser=createAsyncThunk('paste/updateUser',async(user)=>{
    const token = localStorage.getItem('token');
    const response = await axios.put(`${API_URL}/user`, user, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    return response.data;
})

export const deletePastes = createAsyncThunk('paste/deletePaste', async (pasteId) => {
    const token = localStorage.getItem('token');
    await axios.delete(`${API_URL}/paste/id/${pasteId}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
    return pasteId;
})



export const pasteSlice = createSlice({
    name: 'paste',
    initialState,
    reducers: {
        deletePastesFromStore(state,action){
            const index=action.payload;
            state.pastes = state.pastes.filter(p => p._id !== index);
        },
        addPasteInStore(state,action){
            state.pastes.push(action.payload);
        },
        updatePasteInStore(state,action){
            const temp=action.payload;
            const index=state.pastes.findIndex(p=>p._id===temp._id);
            if(index!=-1) state.pastes[index] = temp;
        },
        updateUserInStore(state,action){
            const temp=action.payload;
            if(temp.password===undefined){
                state.user.name=temp.name;
            }
            else
                state.user.password=temp.password;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchPastes.fulfilled, (state, action) => {
                state.loading=false;
                state.pastes = action.payload;
            })
            .addCase(fetchPastes.pending,(state)=>{
                state.loading=true;
            })
            .addCase(fetchPastes.rejected,(state)=>{
                state.loading=false;
            })
            .addCase(addPaste.fulfilled,()=>{
                toast.success("Paste Created Sucessfully ");
            })
            .addCase(updatePaste.fulfilled,()=>{
                toast.success("Paste updated");
            })
            .addCase(updateUser.fulfilled,()=>{
                toast.success("User Updated");
            })
            .addCase(updateUser.rejected,()=>{
                toast.error("Error");
            })
            .addCase(deletePastes.fulfilled,()=>{
                toast.success("Paste deleted");
            })
            .addCase(deletePastes.rejected,()=>{
                toast.error("Failed to delete paste");
            })
            .addCase(fetchUser.fulfilled,(state,action)=>{
                state.user=action.payload;
                state.loading=false;
            }).addCase(fetchUser.pending,(state)=>{
                state.loading=true;
            })
            .addCase(fetchUser.rejected,(state)=>{
                state.loading=false;
                
            })
    }
})

export default pasteSlice.reducer;
export const {addPasteInStore,updatePasteInStore,deletePastesFromStore,updateUserInStore}=pasteSlice.actions;