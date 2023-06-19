import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom/cjs/react-router-dom';

const bookItem = (props) => {
  const { id, title } = props.book; // 구조 변환

  return (
    <Card>
      <Card.Body>
        <Card.Title>{title}</Card.Title>
        <Link to={'/book/' + id} className="btn btn-primary">
          상세보기11
        </Link>
      </Card.Body>
    </Card>
  );
};

export default bookItem;
