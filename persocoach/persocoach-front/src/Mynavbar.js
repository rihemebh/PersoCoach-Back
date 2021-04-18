import React, { Component } from 'react';
import {Nav,  Navbar,NavDropdown} from 'react-bootstrap';


export default class Mynavbar extends Component {
  render() {

    const homeStyle = {

      marginLeft: "150px",
    };
    const mystyle = {

      marginLeft: "100px",
    };
    return (
      <Navbar bg="white" className="mr-auto" expand="lg">
    
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse >
        <Nav className="d-flex justify-content-start">

          <Nav.Link href="#home" style={homeStyle}>Home</Nav.Link>
          <Nav.Link href="#link" style={mystyle} >Coaches</Nav.Link>
         
          <Nav.Link href="#link" style={mystyle} ></Nav.Link>
        </Nav>
        <Navbar.Brand href="#home" style={{marginRight: "100px" , marginLeft: "100px" }} 
        className="d-flex justify-content-center" ><img src="11.png" height="155px" width="292px"></img></Navbar.Brand>
        <Nav className="d-flex justify-content-end" id="basic-navbar-nav">
        <Nav.Link href="#link" style={mystyle}>Community</Nav.Link>
          <Nav.Link href="#link" style={mystyle}>Contact</Nav.Link>
          <Nav.Link href="#link" ></Nav.Link>
          </Nav>
      </Navbar.Collapse>
    </Navbar>
    )
  }
}

