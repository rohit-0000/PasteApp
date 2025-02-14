import './App.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import NavBar from './Components/NavBar';
import Home from './Components/Home';
import Paste from './Components/Paste';
import ViewPaste from './Components/ViewPaste';

const router=createBrowserRouter(
  [
    {
      path:"/",
      element:
      <div>
        <NavBar/>
        <Home/>
      </div>
    },
    {
      path:"/pastes",
      element:
      <div>
        <NavBar/>
        <Paste/>
      </div>
    },
    {
      path:"/pastes/:id", //route parameter use Params
      element:
      <div>
        <NavBar/>
        <ViewPaste/>
      </div>
    }
  ]
)
function App() {

  return (
    <div>
      <RouterProvider router={router}/>
    </div>
  )
}

export default App
