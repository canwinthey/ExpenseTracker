import React, { useEffect, useState } from 'react';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';

function AppToaster({showToaster,setShowToaster,message,variant}) {



  return (
    <Row>
      <Col xs={6}>
      <ToastContainer  style={{ zIndex: 9999, position:'fixed',top:'50 !important' }} position='top-center'>
        <Toast bg={variant.toLowerCase()} onClose={() => setShowToaster(false)} show={showToaster} delay={5000}  autohide>         
          <Toast.Body className='text-white'>{message}</Toast.Body>
        </Toast>
        </ToastContainer>
      </Col>
    </Row>
  );
}

export default AppToaster;