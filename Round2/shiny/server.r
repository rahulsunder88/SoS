library(twitteR)
library(tm)
library(wordcloud)
library(RColorBrewer)
library(dplyr)
library(ggmap)
library(ggplot2)   
library(stringr)
library(ROAuth)
library(ggmap)

shinyServer(function(input, output) {
        
        sliderValues <- eventReactive(input$predict, {
                
                
                if(input$Altimeter > 5000 && input$Temperature > 50 && input$WaterLevel > 2)
                {
                        
                        final2 <- "Flood Warning"
                        final2
                        
                        
                        
                }
                else if(input$Seismometer >= 5 & input$Seismometer <= 6 )
                {
                        
                        final2 <- "Mild Earthqauke warning"
                        final2
                        
                }
                else if(input$Seismometer >= 6 && input$Seismometer <= 8)
                {
                        
                        final2 <- "Strong Earthqauke warning"
                        final2
                        
                }
                else if(input$Seismometer > 8 )
                {
                        
                        final2 <- "Severe Earthqauke warning"
                        final2
                        
                }
                else
                {
                        final2 <- "Conditions are Normal"
                }
                
        })
        
        
        output$mytext <- renderText({
                sliderValues()
        })
        
        
})
