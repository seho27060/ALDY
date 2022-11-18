import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Doughnut } from "react-chartjs-2";
import TierData from "../../data/tier";

const StudyChartTier = ({ studyData }) => {
  const labels = Object.keys(studyData).map((e) => {
    e = TierData[Number(e)];
    return e;
  });
  const data = Object.values(studyData);
  console.log(labels, "label");
  console.log(data, "data");
  ChartJS.register(ArcElement, Tooltip, Legend);

  const expData = {
    labels: labels,
    datasets: [
      {
        labels: labels,
        data: data,
        borderWidth: 2,
        hoverBorderWidth: 3,
        backgroundColor: [
          "rgba(238, 102, 121, 1)",
          "rgba(98, 181, 229, 1)",
          "rgba(255, 198, 0, 1)",
          "rgb(255, 99, 132)",
          "rgb(255, 159, 64)",
          "rgb(255, 205, 86)",
          "rgb(75, 192, 192)",
          "rgb(54, 162, 235)",
        ],
        fill: true,
        hoverOffset: 5,
      },
    ],
  };

  return (
    <div>
      <Doughnut
        options={{
          Legend: {
            display: false,
          },
        }}
        data={expData}
        height={250}
        width={250}
        style={{ padding: "20px" }}
      />
    </div>
  );
};

export default StudyChartTier;
