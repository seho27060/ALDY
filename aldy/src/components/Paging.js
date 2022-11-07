import React, { useState } from "react";
import "./Paging.css";
import Pagination from "react-js-pagination";
import { FiChevronRight, FiChevronLeft, FiChevronsRight, FiChevronsLeft } from "react-icons/fi";

const Paging = ({ page, setPage, totalElements }) => {
  return (
    <Pagination
      activePage={page}
      itemsCountPerPage={10}
      totalItemsCount={totalElements}
      pageRangeDisplayed={10}
      prevPageText={"<"}
      nextPageText={">"}
      onChange={setPage}
    />
  );
};

export default Paging;
