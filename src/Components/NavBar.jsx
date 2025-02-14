import React from 'react'
import { NavLink } from 'react-router-dom'
import './NavBar.css'

const NavBar = () => {
  return (
    <div id='Nav-Container'>
      <div id='Nav'>
        <NavLink className={({isActive})=>isActive? "nav-item-active" : "nav-item"}  to="/">Home</NavLink>
        <NavLink className={({isActive})=>isActive? "nav-item-active" : "nav-item"} to="/pastes">Pastes</NavLink>
      </div>
    </div>
  )
}

export default NavBar